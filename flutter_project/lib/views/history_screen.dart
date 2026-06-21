import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import '../viewmodels/quiz_viewmodel.dart';

class HistoryScreen extends StatelessWidget {
  const HistoryScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final viewModel = context.watch<QuizViewModel>();
    final history = viewModel.historyList;

    if (history.isEmpty) {
      return Center(
        child: Padding(
          padding: const EdgeInsets.all(32.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                padding: const EdgeInsets.all(20),
                decoration: BoxDecoration(
                  color: const Color(0xFF1E293B),
                  shape: BoxShape.circle,
                  border: Border.all(color: const Color(0xFF334155)),
                ),
                child: const Icon(
                  Icons.history_rounded,
                  color: Color(0xFF94A3B8),
                  size: 48,
                ),
              ),
              const SizedBox(height: 20),
              Text(
                "No Exam Logs",
                style: GoogleFonts.plusJakartaSans(
                  color: Colors.white,
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              Text(
                "Your completed exam simulators and subject practice trials will be stored here to dynamically track your accuracy trends.",
                textAlign: TextAlign.center,
                style: GoogleFonts.inter(
                  color: const Color(0xFF94A3B8),
                  fontSize: 13,
                  height: 1.5,
                ),
              ),
            ],
          ),
        ),
      );
    }

    return Column(
      children: [
        // Top Summary Metric
        Container(
          width: double.infinity,
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 16),
          color: const Color(0xFF1E293B),
          child: Row(
            children: [
              Text(
                "Completed Sessions (${history.length})",
                style: GoogleFonts.plusJakartaSans(
                  color: Colors.white,
                  fontSize: 14,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const Spacer(),
              TextButton.icon(
                icon: const Icon(Icons.delete_forever_rounded, color: Color(0xFFEF4444), size: 16),
                label: Text(
                  "Clear All",
                  style: GoogleFonts.plusJakartaSans(
                    color: const Color(0xFFEF4444),
                    fontSize: 12,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                onPressed: () {
                  // Show verification popup dialog
                  showDialog(
                    context: context,
                    builder: (context) => AlertDialog(
                      backgroundColor: const Color(0xFF1E293B),
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                      title: Text(
                        "Clear History",
                        style: GoogleFonts.plusJakartaSans(color: Colors.white, fontWeight: FontWeight.bold),
                      ),
                      content: Text(
                        "Are you sure you want to permanently delete all past session results? This cannot be undone.",
                        style: GoogleFonts.inter(color: const Color(0xFF94A3B8), fontSize: 13),
                      ),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.pop(context),
                          child: Text("Cancel", style: GoogleFonts.plusJakartaSans(color: Colors.white)),
                        ),
                        TextButton(
                          onPressed: () {
                            viewModel.clearAllHistory();
                            Navigator.pop(context);
                          },
                          child: Text("Clear", style: GoogleFonts.plusJakartaSans(color: const Color(0xFFEF4444), fontWeight: FontWeight.bold)),
                        ),
                      ],
                    ),
                  );
                },
              )
            ],
          ),
        ),
        const Divider(color: Color(0xFF334155), height: 1),

        // Scrollable List of Logs
        Expanded(
          child: ListView.builder(
            padding: const EdgeInsets.all(20.0),
            itemCount: history.length,
            itemBuilder: (context, index) {
              final log = history[index];
              final pct = log.totalQuestions > 0 ? (log.score / log.totalQuestions * 100).round() : 0;
              
              Color accentColor = const Color(0xFFEF4444);
              if (pct >= 85) {
                accentColor = const Color(0xFF10B981);
              } else if (pct >= 60) {
                accentColor = const Color(0xFF3B82F6);
              }

              final dateStr = DateFormat('MMM dd, yyyy • h:mm a').format(
                DateTime.fromMillisecondsSinceEpoch(log.timestamp),
              );

              return Container(
                margin: const EdgeInsets.only(bottom: 12),
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: const Color(0xFF1E293B),
                  borderRadius: BorderRadius.circular(16),
                  border: Border.all(color: const Color(0xFF334155)),
                ),
                child: Row(
                  children: [
                    // Left Percent Circle
                    Container(
                      width: 50,
                      height: 50,
                      decoration: BoxDecoration(
                        color: accentColor.withOpacity(0.1),
                        shape: BoxShape.circle,
                        border: Border.all(color: accentColor, width: 2),
                      ),
                      alignment: Alignment.center,
                      child: Text(
                        "$pct%",
                        style: GoogleFonts.plusJakartaSans(
                          color: Colors.white,
                          fontSize: 12,
                          fontWeight: FontWeight.black,
                        ),
                      ),
                    ),
                    const SizedBox(width: 16),

                    // Middle Session Info
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            log.subject,
                            style: GoogleFonts.plusJakartaSans(
                              color: Colors.white,
                              fontSize: 14,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 4),
                          Text(
                            dateStr,
                            style: GoogleFonts.inter(
                              color: const Color(0xFF94A3B8),
                              fontSize: 11,
                            ),
                          ),
                        ],
                      ),
                    ),

                    // Right raw Score and time metrics
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        Text(
                          "${log.score}/${log.totalQuestions}",
                          style: GoogleFonts.plusJakartaSans(
                            color: Colors.white,
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Row(
                          children: [
                            const Icon(Icons.access_time_outlined, color: Color(0xFF94A3B8), size: 12),
                            const SizedBox(width: 4),
                            Text(
                              _formatTime(log.elapsedSeconds),
                              style: GoogleFonts.inter(
                                color: const Color(0xFF94A3B8),
                                fontSize: 11,
                              ),
                            ),
                          ],
                        )
                      ],
                    )
                  ],
                ),
              );
            },
          ),
        ),
      ],
    );
  }

  String _formatTime(int totalSecs) {
    final mins = totalSecs ~/ 60;
    final secs = totalSecs % 60;
    return '${mins.toString().padLeft(2, '0')}:${secs.toString().padLeft(2, '0')}';
  }
}
