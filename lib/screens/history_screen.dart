import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/quiz_provider.dart';

class HistoryScreen extends StatelessWidget {
  const HistoryScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<QuizProvider>(context);
    final historyList = provider.historyList;

    // Color Palette Coordinates
    const slateBg = Color(0xFF0A0E17);
    const slateSurface = Color(0xFF161F30);
    const slateBorder = Color(0xFF26354D);
    const blueAccent = Color(0xFF3B82F6);
    const emeraldAccent = Color(0xFF10B981);
    const redAccent = Color(0xFFEF4444);
    const textMuted = Color(0xFF8A9BAE);

    // Date formatting helper
    String formatTimestamp(int milliseconds) {
      try {
        final date = DateTime.fromMillisecondsSinceEpoch(milliseconds);
        final months = [
          "Jan", "Feb", "Mar", "Apr", "May", "Jun", 
          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        ];
        final monthStr = months[date.month - 1];
        final dayStr = date.day.toString().padLeft(2, '0');
        final hour = date.hour > 12 ? date.hour - 12 : (date.hour == 0 ? 12 : date.hour);
        final minuteStr = date.minute.toString().padLeft(2, '0');
        final ampm = date.hour >= 12 ? "PM" : "AM";
        return "$monthStr $dayStr, ${date.year} • ${hour.toString().padLeft(2, '0')}:$minuteStr $ampm";
      } catch (e) {
        return milliseconds.toString();
      }
    }

    if (historyList.isEmpty) {
      return Scaffold(
        backgroundColor: slateBg,
        appBar: AppBar(
          backgroundColor: slateBg,
          elevation: 0,
          leading: IconButton(
            icon: const Icon(Icons.arrow_back, color: Colors.white),
            onPressed: () => provider.navigateTo(QuizScreenType.dashboard),
          ),
          title: const Text(
            "Performance History",
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.white),
          ),
        ),
        body: Center(
          child: Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(
                  Icons.assignment,
                  color: textMuted.withOpacity(0.4),
                  size: 72,
                ),
                const SizedBox(height: 16),
                const Text(
                  "No Practiced History",
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
                const SizedBox(height: 6),
                const Text(
                  "Historical performance logs of your subject drills will show up here after completing quizzes.",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 13.5,
                    color: textMuted,
                    height: 1.45,
                  ),
                ),
              ],
            ),
          ),
        ),
      );
    }

    // Cumulative Statistics Calculations
    final totalQuestions = historyList.fold<int>(0, (sum, item) => sum + item.totalCount);
    final totalScore = historyList.fold<int>(0, (sum, item) => sum + item.score);
    final avgAccuracy = totalQuestions > 0 ? (totalScore * 100) ~/ totalQuestions : 0;

    return Scaffold(
      backgroundColor: slateBg,
      appBar: AppBar(
        backgroundColor: slateBg,
        elevation: 0,
        scrolledUnderElevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () => provider.navigateTo(QuizScreenType.dashboard),
        ),
        title: const Text(
          "Performance History",
          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.white),
        ),
      ),
      body: SafeArea(
        child: Column(
          children: [
            // Cumulative aggregate stats overview card
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Container(
                decoration: BoxDecoration(
                  color: slateSurface,
                  border: Border.all(color: slateBorder),
                  borderRadius: BorderRadius.circular(12),
                ),
                padding: const EdgeInsets.all(18.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          "OVERALL ACCURACY",
                          style: TextStyle(
                            fontSize: 11,
                            fontWeight: FontWeight.bold,
                            color: textMuted,
                            letterSpacing: 0.5,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          "$avgAccuracy%",
                          style: const TextStyle(
                            fontSize: 28,
                            fontWeight: FontWeight.w900,
                            color: emeraldAccent,
                          ),
                        ),
                      ],
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        const Text(
                          "TOTAL DRILLS",
                          style: TextStyle(
                            fontSize: 11,
                            fontWeight: FontWeight.bold,
                            color: textMuted,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          "${historyList.length} sessions",
                          style: const TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),

            // Controls header bar
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 4.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    "Session Logs",
                    style: TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  TextButton.icon(
                    key: const Key("clear_history_btn"),
                    onPressed: () => provider.clearAllHistory(),
                    icon: const Icon(Icons.delete, color: redAccent, size: 16),
                    label: const Text(
                      "Clear All",
                      style: TextStyle(
                        fontSize: 13,
                        fontWeight: FontWeight.bold,
                        color: redAccent,
                      ),
                    ),
                  ),
                ],
              ),
            ),

            // List of timeline entries
            Expanded(
              child: ListView.builder(
                padding: const EdgeInsets.symmetric(horizontal: 16.0),
                itemCount: historyList.length,
                itemBuilder: (context, i) {
                  final log = historyList[i];
                  final rate = log.totalCount > 0 ? (log.score * 100) ~/ log.totalCount : 0;

                  Color rateColor = redAccent;
                  if (rate >= 80) {
                    rateColor = emeraldAccent;
                  } else if (rate >= 50) {
                    rateColor = blueAccent;
                  }

                  return Container(
                    margin: const EdgeInsets.only(bottom: 12.0),
                    decoration: BoxDecoration(
                      color: slateSurface,
                      border: Border.all(color: slateBorder),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    padding: const EdgeInsets.all(14.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              log.subject,
                              style: const TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                              ),
                            ),
                            const SizedBox(height: 2),
                            Text(
                              formatTimestamp(log.timestamp),
                              style: const TextStyle(fontSize: 12, color: textMuted),
                            ),
                          ],
                        ),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            Text(
                              "${log.score}/${log.totalCount}",
                              style: TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.black,
                                color: rateColor,
                              ),
                            ),
                            const SizedBox(height: 2),
                            Text(
                              "$rate% Accuracy",
                              style: const TextStyle(fontSize: 11, color: textMuted),
                            ),
                          ],
                        ),
                      ],
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
