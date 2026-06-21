import 'dart:async';
import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/question.dart';
import '../models/bookmark.dart';
import '../models/quiz_history.dart';
import '../data/question_bank.dart';

enum Screen {
  dashboard,
  quiz,
  result,
  bookmarks,
  history,
}

class QuizViewModel extends ChangeNotifier {
  // Navigation State
  Screen _currentScreen = Screen.dashboard;
  Screen get currentScreen => _currentScreen;

  // Quiz Session State
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

  // Saved Persistence States
  List<Bookmark> _bookmarks = [];
  List<Bookmark> get bookmarks => _bookmarks;

  List<QuizHistory> _historyList = [];
  List<QuizHistory> get historyList => _historyList;

  Timer? _timer;
  SharedPreferences? _prefs;

  QuizViewModel() {
    _initPreferences();
  }

  Future<void> _initPreferences() async {
    _prefs = await SharedPreferences.getInstance();
    _loadBookmarks();
    _loadHistory();
  }

  // --- PERSISTENCE LOGIC ---
  void _loadBookmarks() {
    if (_prefs == null) return;
    final jsonStr = _prefs!.getString('bookmarks');
    if (jsonStr != null) {
      try {
        final List<dynamic> listMap = json.decode(jsonStr);
        _bookmarks = listMap.map((map) => Bookmark.fromJson(map)).toList();
      } catch (e) {
        if (kDebugMode) print("Error loading bookmarks: $e");
      }
    }
    notifyListeners();
  }

  Future<void> _saveBookmarks() async {
    if (_prefs == null) return;
    final listMap = _bookmarks.map((b) => b.toJson()).toList();
    await _prefs!.setString('bookmarks', json.encode(listMap));
  }

  void _loadHistory() {
    if (_prefs == null) return;
    final jsonStr = _prefs!.getString('history');
    if (jsonStr != null) {
      try {
        final List<dynamic> listMap = json.decode(jsonStr);
        _historyList = listMap.map((map) => QuizHistory.fromJson(map)).toList();
      } catch (e) {
        if (kDebugMode) print("Error loading history: $e");
      }
    }
    notifyListeners();
  }

  Future<void> _saveHistory() async {
    if (_prefs == null) return;
    final listMap = _historyList.map((h) => h.toJson()).toList();
    await _prefs!.setString('history', json.encode(listMap));
  }

  // --- NAVIGATION FLOW ---
  void navigateTo(Screen screen) {
    _currentScreen = screen;
    notifyListeners();
  }

  // --- QUIZ MANAGEMENT ---
  void startQuiz(String subject) {
    _selectedSubject = subject;
    
    // Choose questions
    List<Question> source;
    if (subject == "All Subjects") {
      source = List.from(QuestionBank.questions);
    } else {
      source = QuestionBank.questions.where((q) => q.subject == subject).toList();
    }
    
    source.shuffle();
    // Take exactly 100 questions as requested by the user
    _quizQuestions = source.take(100).toList();
    
    // Reset states
    _currentQuestionIndex = 0;
    _selectedOptionIndex = null;
    _isAnswerVerified = false;
    _questionResults = List.filled(_quizQuestions.length, null);
    _score = 0;
    _elapsedSeconds = 0;
    _isTimerActive = true;
    _currentScreen = Screen.quiz;

    _checkBookmarkStatus();
    _startTimer();
    notifyListeners();
  }

  void _startTimer() {
    _timer?.cancel();
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (_isTimerActive) {
        _elapsedSeconds++;
        notifyListeners();
      }
    });
  }

  void _stopTimer() {
    _isTimerActive = false;
    _timer?.cancel();
  }

  void selectOption(int index) {
    if (_isAnswerVerified) return;
    _selectedOptionIndex = index;
    notifyListeners();
  }

  void verifyAnswer() {
    if (_selectedOptionIndex == null || _isAnswerVerified) return;
    
    _isAnswerVerified = true;
    final currentQ = _quizQuestions[_currentQuestionIndex];
    final isCorrect = _selectedOptionIndex == currentQ.correctOption;
    
    _questionResults[_currentQuestionIndex] = isCorrect;
    if (isCorrect) {
      _score++;
    }
    
    notifyListeners();
  }

  void nextQuestion() {
    if (_currentQuestionIndex + 1 < _quizQuestions.length) {
      _currentQuestionIndex++;
      _selectedOptionIndex = null;
      _isAnswerVerified = false;
      _checkBookmarkStatus();
    } else {
      _completeSession();
    }
    notifyListeners();
  }

  void jumpToQuestion(int index) {
    if (index >= 0 && index < _quizQuestions.length) {
      _currentQuestionIndex = index;
      _selectedOptionIndex = null;
      _isAnswerVerified = false;
      _checkBookmarkStatus();
      notifyListeners();
    }
  }

  void _completeSession() {
    _stopTimer();
    
    // Save to history list
    final newHistory = QuizHistory(
      subject: _selectedSubject,
      score: _score,
      totalQuestions: _quizQuestions.length,
      elapsedSeconds: _elapsedSeconds,
      timestamp: DateTime.now().millisecondsSinceEpoch,
    );
    
    _historyList.insert(0, newHistory);
    _saveHistory();
    
    _currentScreen = Screen.result;
  }

  void resetApp() {
    _stopTimer();
    _currentScreen = Screen.dashboard;
    _selectedSubject = "";
    _quizQuestions = [];
    _currentQuestionIndex = 0;
    _selectedOptionIndex = null;
    _isAnswerVerified = false;
    _questionResults = [];
    _score = 0;
    _elapsedSeconds = 0;
    notifyListeners();
  }

  // --- BOOKMARK OPERATIONS ---
  void _checkBookmarkStatus() {
    if (_quizQuestions.isEmpty || _currentQuestionIndex >= _quizQuestions.length) {
      _isCurrentQuestionBookmarked = false;
      return;
    }
    final currentQ = _quizQuestions[_currentQuestionIndex];
    _isCurrentQuestionBookmarked = _bookmarks.any((b) => b.questionText == currentQ.question);
  }

  void toggleBookmark() {
    if (_quizQuestions.isEmpty || _currentQuestionIndex >= _quizQuestions.length) return;
    
    final currentQ = _quizQuestions[_currentQuestionIndex];
    final existIndex = _bookmarks.indexWhere((b) => b.questionText == currentQ.question);
    
    if (existIndex >= 0) {
      _bookmarks.removeAt(existIndex);
      _isCurrentQuestionBookmarked = false;
    } else {
      final newBookmark = Bookmark(
        questionText: currentQ.question,
        subject: currentQ.subject,
        optionA: currentQ.optionA,
        optionB: currentQ.optionB,
        optionC: currentQ.optionC,
        optionD: currentQ.optionD,
        correctOption: currentQ.correctOption,
        explanation: currentQ.explanation,
        timestamp: DateTime.now().millisecondsSinceEpoch,
      );
      _bookmarks.add(newBookmark);
      _isCurrentQuestionBookmarked = true;
    }
    
    _saveBookmarks();
    notifyListeners();
  }

  void deleteBookmarkDirectly(String questionText) {
    _bookmarks.removeWhere((b) => b.questionText == questionText);
    _saveBookmarks();
    _checkBookmarkStatus();
    notifyListeners();
  }

  void clearAllHistory() {
    _historyList.clear();
    _saveHistory();
    notifyListeners();
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }
}
