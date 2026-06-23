import 'dart:async';
import 'package:flutter/material.dart';
import '../data/question_bank.dart';
import '../database/database_helper.dart';
import '../models/bookmark.dart';
import '../models/history.dart';
import '../models/question.dart';

enum QuizScreenType {
  dashboard,
  quiz,
  result,
  bookmarks,
  history,
}

class QuizProvider extends ChangeNotifier {
  final DatabaseHelper _dbHelper = DatabaseHelper.instance;

  // Screen state
  QuizScreenType _currentScreen = QuizScreenType.dashboard;
  QuizScreenType get currentScreen => _currentScreen;

  // Active quiz state parameters
  String _selectedSubject = "";
  String get selectedSubject => _selectedSubject;

  List<Question> _quizQuestions = [];
  List<Question> get quizQuestions => _quizQuestions;

  int _currentQuestionIndex = 0;
  int get currentQuestionIndex => _currentQuestionIndex;

  int? _selectedOptionIndex;
  int? get selectedOptionIndex => _selectedOptionIndex;

  bool _isAnswerVerified = false;
  bool get isAnswerVerified => _isAnswerVerified;

  List<bool?> _questionResults = [];
  List<bool?> get questionResults => _questionResults;

  int _score = 0;
  int get score => _score;

  int _elapsedSeconds = 0;
  int get elapsedSeconds => _elapsedSeconds;

  bool _isTimerActive = false;
  bool get isTimerActive => _isTimerActive;

  bool _isCurrentQuestionBookmarked = false;
  bool get isCurrentQuestionBookmarked => _isCurrentQuestionBookmarked;

  // SQLite Cached Data List
  List<Bookmark> _bookmarks = [];
  List<Bookmark> get bookmarks => _bookmarks;

  List<QuizHistory> _historyList = [];
  List<QuizHistory> get historyList => _historyList;

  Timer? _timer;

  QuizProvider() {
    loadBookmarks();
    loadHistory();
  }

  // Navigation Logic
  void navigateTo(QuizScreenType screen) {
    _currentScreen = screen;
    if (screen != QuizScreenType.quiz) {
      stopTimer();
    }
    notifyListeners();
  }

  // Load Bookmarks from database
  Future<void> loadBookmarks() async {
    _bookmarks = await _dbHelper.getBookmarks();
    await checkCurrentQuestionBookmarked();
    notifyListeners();
  }

  // Load History from database
  Future<void> loadHistory() async {
    _historyList = await _dbHelper.getHistory();
    notifyListeners();
  }

  // Check if current active question is bookmarked
  Future<void> checkCurrentQuestionBookmarked() async {
    if (_currentScreen == QuizScreenType.quiz && _quizQuestions.isNotEmpty && _currentQuestionIndex < _quizQuestions.length) {
      final question = _quizQuestions[_currentQuestionIndex];
      _isCurrentQuestionBookmarked = await _dbHelper.isBookmarked(question.question);
    } else {
      _isCurrentQuestionBookmarked = false;
    }
  }

  // Initialize Quiz Drills
  void startQuiz(String subject) {
    List<Question> filtered;
    if (subject == "All Subjects") {
      filtered = List<Question>.from(QuestionBank.questions)..shuffle();
      filtered = filtered.take(100).toList();
    } else {
      filtered = QuestionBank.questions.where((q) => q.subject == subject).toList()..shuffle();
      filtered = filtered.take(100).toList();
    }

    _currentScreen = QuizScreenType.quiz;
    _selectedSubject = subject;
    _quizQuestions = filtered;
    _currentQuestionIndex = 0;
    _selectedOptionIndex = null;
    _isAnswerVerified = false;
    _questionResults = List<bool?>.filled(filtered.length, null);
    _score = 0;
    _elapsedSeconds = 0;
    _isTimerActive = true;

    checkCurrentQuestionBookmarked();
    startTimer();
    notifyListeners();
  }

  // Timer Controls
  void startTimer() {
    _timer?.cancel();
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (_isTimerActive) {
        _elapsedSeconds++;
        notifyListeners();
      }
    });
  }

  void stopTimer() {
    _isTimerActive = false;
    _timer?.cancel();
  }

  // Option selection
  void selectOption(int index) {
    if (!_isAnswerVerified) {
      _selectedOptionIndex = index;
      notifyListeners();
    }
  }

  // Verify Active Answer
  Future<void> verifyAnswer() async {
    if (_quizQuestions.isEmpty || _selectedOptionIndex == null || _currentQuestionIndex >= _quizQuestions.length) return;

    final question = _quizQuestions[_currentQuestionIndex];
    final isCorrect = _selectedOptionIndex == question.correct;

    _questionResults[_currentQuestionIndex] = isCorrect;
    _isAnswerVerified = true;
    if (isCorrect) {
      _score++;
    }

    notifyListeners();
  }

  // Progress to next question or screen results
  Future<void> nextQuestion() async {
    final nextIndex = _currentQuestionIndex + 1;
    if (nextIndex < _quizQuestions.length) {
      _currentQuestionIndex = nextIndex;
      _selectedOptionIndex = null;
      _isAnswerVerified = false;
      await checkCurrentQuestionBookmarked();
      notifyListeners();
    } else {
      await completeQuiz();
    }
  }

  // Jumping directly to index
  Future<void> jumpToQuestion(int index) async {
    if (index >= 0 && index < _quizQuestions.length) {
      _currentQuestionIndex = index;
      _isAnswerVerified = _questionResults[index] != null;
      _selectedOptionIndex = null; // reset dynamic selection unless already verified
      await checkCurrentQuestionBookmarked();
      notifyListeners();
    }
  }

  // Wrap up active quiz and save history
  Future<void> completeQuiz() async {
    stopTimer();
    _currentScreen = QuizScreenType.result;
    notifyListeners();

    final history = QuizHistory(
      subject: _selectedSubject,
      score: _score,
      totalCount: _quizQuestions.length,
      timestamp: DateTime.now().millisecondsSinceEpoch,
    );

    await _dbHelper.insertHistory(history);
    await loadHistory();
  }

  // Toggle saving of bookmark
  Future<void> toggleBookmark() async {
    if (_quizQuestions.isEmpty || _currentQuestionIndex >= _quizQuestions.length) return;

    final question = _quizQuestions[_currentQuestionIndex];
    final exists = _isCurrentQuestionBookmarked;

    if (exists) {
      await _dbHelper.deleteBookmark(question.question);
      _isCurrentQuestionBookmarked = false;
    } else {
      final b = Bookmark(
        questionText: question.question,
        subject: question.subject,
        optionsString: question.options.join(";;"),
        correctIndex: question.correct,
        explanation: question.explanation,
        timestamp: DateTime.now().millisecondsSinceEpoch,
      );
      await _dbHelper.insertBookmark(b);
      _isCurrentQuestionBookmarked = true;
    }

    await loadBookmarks();
  }

  // Delete directly from bookmarks folder screen
  Future<void> deleteBookmarkDirectly(String questionText) async {
    await _dbHelper.deleteBookmark(questionText);
    await loadBookmarks();
  }

  // Purge performance logs
  Future<void> clearAllHistory() async {
    await _dbHelper.clearHistory();
    await loadHistory();
  }

  // Soft Reset App State
  void resetApp() {
    stopTimer();
    _currentScreen = QuizScreenType.dashboard;
    _selectedSubject = "";
    _quizQuestions = [];
    _currentQuestionIndex = 0;
    _selectedOptionIndex = null;
    _isAnswerVerified = false;
    _questionResults = [];
    _score = 0;
    _elapsedSeconds = 0;
    _isTimerActive = false;
    _isCurrentQuestionBookmarked = false;
    notifyListeners();
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }
}
