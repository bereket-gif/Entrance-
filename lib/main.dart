import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'providers/quiz_provider.dart';
import 'screens/bookmarks_screen.dart';
import 'screens/dashboard_screen.dart';
import 'screens/history_screen.dart';
import 'screens/quiz_screen.dart';
import 'screens/result_screen.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(
    ChangeNotifierProvider(
      create: (_) => QuizProvider(),
      child: const EthioMatricApp(),
    ),
  );
}

class EthioMatricApp extends StatelessWidget {
  const EthioMatricApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ethio Matric',
      debugShowCheckedModeBanner: false,
      themeMode: ThemeMode.dark,
      darkTheme: ThemeData(
        brightness: Brightness.dark,
        useMaterial3: true,
        fontFamily: 'Roboto',
      ),
      home: const MainNavigator(),
    );
  }
}

class MainNavigator extends StatelessWidget {
  const MainNavigator({super.key});

  @override
  Widget build(BuildContext context) {
    final currentScreen = context.watch<QuizProvider>().currentScreen;

    // Cross-screen routes listener
    switch (currentScreen) {
      case QuizScreenType.dashboard:
        return const DashboardScreen();
      case QuizScreenType.quiz:
        return const QuizScreen();
      case QuizScreenType.result:
        return const ResultScreen();
      case QuizScreenType.bookmarks:
        return const BookmarksScreen();
      case QuizScreenType.history:
        return const HistoryScreen();
    }
  }
}
