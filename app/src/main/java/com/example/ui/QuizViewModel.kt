package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class Screen {
    DASHBOARD,
    QUIZ,
    RESULT,
    BOOKMARKS,
    HISTORY
}

data class QuizUiState(
    val currentScreen: Screen = Screen.DASHBOARD,
    
    // Quiz Session Configurations
    val selectedSubject: String = "",
    val quizQuestions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerVerified: Boolean = false,
    val questionResults: List<Boolean?> = emptyList(), // Store whether each question was correct (true), incorrect (false), or unanswered (null)
    val score: Int = 0,
    val elapsedSeconds: Int = 0,
    val isTimerActive: Boolean = false,
    
    // Bookmarking state for current question
    val isCurrentQuestionBookmarked: Boolean = false
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = ExamRepository(database.bookmarkDao(), database.quizHistoryDao())

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    val allBookmarks: StateFlow<List<BookmarkEntity>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allHistory: StateFlow<List<QuizHistoryEntity>> = repository.allHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var timerJob: Job? = null

    init {
        // Automatically check if the current question is bookmarked
        viewModelScope.launch {
            _uiState.map { Pair(it.currentScreen, it.currentQuestionIndex) }
                .distinctUntilChanged()
                .collect { (screen, index) ->
                    val currentState = _uiState.value
                    if (screen == Screen.QUIZ && index < currentState.quizQuestions.size) {
                        val question = currentState.quizQuestions[index]
                        val bookmarked = repository.isBookmarked(question.question)
                        _uiState.update { it.copy(isCurrentQuestionBookmarked = bookmarked) }
                    }
                }
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen) }
        if (screen != Screen.QUIZ) {
            stopTimer()
        }
    }

    fun startQuiz(subject: String) {
        val filtered = if (subject == "All Subjects") {
            QuestionBank.questions.shuffled().take(100)
        } else {
            QuestionBank.questions.filter { it.subject == subject }.shuffled().take(100)
        }
        
        _uiState.update {
            it.copy(
                currentScreen = Screen.QUIZ,
                selectedSubject = subject,
                quizQuestions = filtered,
                currentQuestionIndex = 0,
                selectedOptionIndex = null,
                isAnswerVerified = false,
                questionResults = List(filtered.size) { null },
                score = 0,
                elapsedSeconds = 0,
                isTimerActive = true
            )
        }
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update {
                    if (it.isTimerActive) {
                        it.copy(elapsedSeconds = it.elapsedSeconds + 1)
                    } else {
                        it
                    }
                }
            }
        }
    }

    private fun stopTimer() {
        _uiState.update { it.copy(isTimerActive = false) }
        timerJob?.cancel()
    }

    fun selectOption(index: Int) {
        if (!_uiState.value.isAnswerVerified) {
            _uiState.update { it.copy(selectedOptionIndex = index) }
        }
    }

    fun verifyAnswer() {
        val state = _uiState.value
        val currentIndex = state.currentQuestionIndex
        val question = state.quizQuestions.getOrNull(currentIndex) ?: return
        val selectedIndex = state.selectedOptionIndex ?: return

        // Verify if correct
        val isCorrect = selectedIndex == question.correct
        val updatedResults = state.questionResults.toMutableList()
        updatedResults[currentIndex] = isCorrect

        _uiState.update {
            it.copy(
                isAnswerVerified = true,
                questionResults = updatedResults,
                score = if (isCorrect) state.score + 1 else state.score
            )
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentQuestionIndex + 1
        if (nextIndex < state.quizQuestions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    selectedOptionIndex = null,
                    isAnswerVerified = false
                )
            }
        } else {
            completeQuiz()
        }
    }

    fun jumpToQuestion(index: Int) {
        val state = _uiState.value
        if (index >= 0 && index < state.quizQuestions.size) {
            val hasResult = state.questionResults[index] != null
            _uiState.update {
                it.copy(
                    currentQuestionIndex = index,
                    isAnswerVerified = hasResult,
                    selectedOptionIndex = null // Clear selecting unless already completed
                )
            }
        }
    }

    private fun completeQuiz() {
        stopTimer()
        val state = _uiState.value
        _uiState.update { it.copy(currentScreen = Screen.RESULT) }

        // Output to history DB asynchronously
        viewModelScope.launch {
            repository.insertHistory(
                QuizHistoryEntity(
                    subject = state.selectedSubject,
                    score = state.score,
                    totalCount = state.quizQuestions.size
                )
            )
        }
    }

    fun toggleBookmark() {
        val state = _uiState.value
        val currentIndex = state.currentQuestionIndex
        val question = state.quizQuestions.getOrNull(currentIndex) ?: return
        val currentlyBookmarked = state.isCurrentQuestionBookmarked

        viewModelScope.launch {
            if (currentlyBookmarked) {
                repository.deleteBookmark(question.question)
                _uiState.update { it.copy(isCurrentQuestionBookmarked = false) }
            } else {
                repository.insertBookmark(
                    BookmarkEntity(
                        questionText = question.question,
                        subject = question.subject,
                        optionsString = question.options.joinToString(";;"),
                        correctIndex = question.correct,
                        explanation = question.explanation
                    )
                )
                _uiState.update { it.copy(isCurrentQuestionBookmarked = true) }
            }
        }
    }

    fun deleteBookmarkDirectly(questionText: String) {
        viewModelScope.launch {
            repository.deleteBookmark(questionText)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    fun resetApp() {
        stopTimer()
        _uiState.update { QuizUiState() }
    }
}
