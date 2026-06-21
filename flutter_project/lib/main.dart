import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';
import 'viewmodels/quiz_viewmodel.dart';
import 'views/dashboard_screen.dart';
import 'views/quiz_screen.dart';
import 'views/result_screen.dart';
import 'views/bookmarks_screen.dart';
import 'views/history_screen.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
    statusBarColor: Colors.transparent,
    statusBarIconBrightness: Brightness.light,
  ));
  runApp(
    ChangeNotifierProvider(
      create: (_) => QuizViewModel(),
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ethio Matric',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        useMaterial3: true,
        brightness: Brightness.dark,
        scaffoldBackgroundColor: const Color(0xFF0F172A), // Slate 900
        canvasColor: const Color(0xFF1E293B), // Slate 800
        dividerTheme: const DividerThemeData(color: Color(0xFF334155)),
        colorScheme: const ColorScheme.dark(
          primary: Color(0xFF3B82F6),
          secondary: Color(0xFF10B981),
          surface: Color(0xFF1E293B),
          background: Color(0xFF0F172A),
        ),
      ),
      home: const MainGate(),
    );
  }
}

class MainGate extends StatelessWidget {
  const MainGate({super.key});

  @override
  Widget build(BuildContext context) {
    final viewModel = context.watch<QuizViewModel>();
    final currentScreen = viewModel.currentScreen;

    // Build adaptive screen header title
    String headerTitle = "Ethio Matric";
    if (currentScreen == Screen.quiz) {
      headerTitle = "${viewModel.selectedSubject} Quiz";
    } else if (currentScreen == Screen.result) {
      headerTitle = "Session Complete";
    } else if (currentScreen == Screen.bookmarks) {
      headerTitle = "Saved Bookmarks";
    } else if (currentScreen == Screen.history) {
      headerTitle = "Performance Logs";
    }

    final String? timerText = currentScreen == Screen.quiz
        ? _formatTime(viewModel.elapsedSeconds)
        : null;

    return WillPopScope(
      onWillPop: () async {
        if (currentScreen != Screen.dashboard) {
          if (currentScreen == Screen.quiz || currentScreen == Screen.result) {
            viewModel.resetApp();
          } else {
            viewModel.navigateTo(Screen.dashboard);
          }
          return false;
        }
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xFF1E293B),
          elevation: 4,
          shadowColor: Colors.black.withOpacity(0.3),
          title: Text(
            headerTitle,
            style: GoogleFonts.plusJakartaSans(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontSize: 18,
            ),
          ),
          centerTitle: false,
          leading: currentScreen != Screen.dashboard
              ? IconButton(
                  icon: const Icon(Icons.arrow_back_ios_new_rounded, color: Colors.white, size: 18),
                  onPressed: () {
                    if (currentScreen == Screen.quiz || currentScreen == Screen.result) {
                      viewModel.resetApp();
                    } else {
                      viewModel.navigateTo(Screen.dashboard);
                    }
                  },
                )
              : null,
          actions: [
            if (timerText != null)
              Container(
                margin: const EdgeInsets.only(right: 16.0),
                padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                decoration: BoxDecoration(
                  color: const Color(0xFF0F172A),
                  borderRadius: BorderRadius.circular(12),
                  border: Border.all(color: const Color(0xFF334155)),
                ),
                child: Row(
                  children: [
                    const Icon(Icons.access_time_rounded, color: Color(0xFFEF4444), size: 14),
                    const SizedBox(width: 6),
                    Text(
                      timerText,
                      style: GoogleFonts.inter(
                        color: Colors.white,
                        fontSize: 12,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ],
                ),
              )
          ],
        ),
        body: AnimatedSwitcher(
          duration: const Duration(milliseconds: 300),
          child: _getScreenContent(currentScreen),
        ),
      ),
    );
  }

  Widget _getScreenContent(Screen screen) {
    switch (screen) {
      case Screen.dashboard:
        return const DashboardScreen(key: ValueKey("Dashboard"));
      case Screen.quiz:
        return const QuizScreen(key: ValueKey("Quiz"));
      case Screen.result:
        return const ResultScreen(key: ValueKey("Result"));
      case Screen.bookmarks:
        return const BookmarksScreen(key: ValueKey("Bookmarks"));
      case Screen.history:
        return const HistoryScreen(key: ValueKey("History"));
    }
  }

  String _formatTime(int totalSecs) {
    final mins = totalSecs ~/ 60;
    final secs = totalSecs % 60;
    return '${mins.toString().padLeft(2, '0')}:${secs.toString().padLeft(2, '0')}';
  }
}
