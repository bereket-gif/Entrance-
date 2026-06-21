import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';
import '../viewmodels/quiz_viewmodel.dart';

class ResultScreen extends StatelessWidget {
  const ResultScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final viewModel = context.watch<QuizViewModel>();
    final total = viewModel.quizQuestions.length;
    final score = viewModel.score;
    final pct = total > 0 ? (score / total * 100).round() : 0;
    
    // Determine motivational feedback message
    String verdict = "Needs Practice";
    String info = "Keep studying to sharpen your speed and memory.";
    Color accentColor = const Color(0xFFEF4444); // Red
    
    if (pct >= 85) {
      verdict = "Excellent Work!";
      info = "You are exceptionally prepared for the national matric exam!";
      accentColor = const Color(0xFF10B981); // Emerald
    } else if (pct >= 60) {
      verdict = "Good Effort!";
      info = "Solid baseline! Revise explanations of wrong answers to achieve mastery.";
      accentColor = const Color(0xFF3B82F6); // Blue
    }

    final durationText = _formatTime(viewModel.elapsedSeconds);

    return SingleChildScrollView(
      padding: const EdgeInsets.all(24.0),
      child: Column(
        children: [
          const SizedBox(height: 10),
          // Results Card
          Container(
            padding: const EdgeInsets.all(28.0),
            decoration: BoxDecoration(
              color: const Color(0xFF1E293B),
              borderRadius: BorderRadius.circular(24.0),
              border: Border.all(color: const Color(0xFF334155), width: 1.5),
            ),
            child: Column(
              children: [
                // Circular Ring Score Display
                Stack(
                  alignment: Alignment.center,
                  children: [
                    SizedBox(
                      width: 140,
                      height: 140,
                      child: CircularProgressIndicator(
                        value: pct / 100,
                        strokeWidth: 10,
                        backgroundColor: const Color(0xFF0F172A),
                        color: accentColor,
                      ),
                    ),
                    Column(
                      children: [
                        Text(
                          "$pct%",
                          style: GoogleFonts.plusJakartaSans(
                            color: Colors.white,
                            fontSize: 32,
                            fontWeight: FontWeight.black,
                          ),
                        ),
                        Text(
                          "$score / $total Correct",
                          style: GoogleFonts.inter(
                            color: const Color(0xFF94A3B8),
                            fontSize: 12,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
                const SizedBox(height: 24),

                // Verdict Msg
                Text(
                  verdict,
                  style: GoogleFonts.plusJakartaSans(
                    color: Colors.white,
                    fontSize: 22,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const SizedBox(height: 8),
                Text(
                  info,
                  textAlign: TextAlign.center,
                  style: GoogleFonts.inter(
                    color: const Color(0xFF94A3B8),
                    fontSize: 14,
                    height: 1.5,
                  ),
                ),
                const SizedBox(height: 24),
                const Divider(color: Color(0xFF334155)),
                const SizedBox(height: 16),

                // Specs Breakdown grid
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    _buildSpecCol(
                      label: "DURATION",
                      value: durationText,
                      icon: Icons.timer_outlined,
                      iconColor: const Color(0xFF3B82F6),
                    ),
                    _buildSpecCol(
                      label: "ACCURACY",
                      value: "$pct%",
                      icon: Icons.insights_rounded,
                      iconColor: const Color(0xFF10B981),
                    ),
                    _buildSpecCol(
                      label: "SUBJECT",
                      value: viewModel.selectedSubject,
                      icon: Icons.subject_rounded,
                      iconColor: const Color(0xFFF59E0B),
                    ),
                  ],
                ),
              ],
            ),
          ),
          const SizedBox(height: 32),

          // Action button returning to main menu
          SizedBox(
            width: double.infinity,
            height: 52,
            child: ElevatedButton(
              onPressed: viewModel.resetApp,
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF3B82F6),
                foregroundColor: Colors.white,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(16),
                ),
                elevation: 0,
              ),
              child: Text(
                "Back to Dashboard",
                style: GoogleFonts.plusJakartaSans(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSpecCol({
    required String label,
    required String value,
    required IconData icon,
    required Color iconColor,
  }) {
    return Column(
      children: [
        Icon(icon, color: iconColor, size: 22),
        const SizedBox(height: 6),
        Text(
          label,
          style: GoogleFonts.inter(
            color: const Color(0xFF94A3B8),
            fontSize: 10,
            fontWeight: FontWeight.bold,
            letterSpacing: 1,
          ),
        ),
        const SizedBox(height: 4),
        Text(
          value,
          style: GoogleFonts.plusJakartaSans(
            color: Colors.white,
            fontSize: 13,
            fontWeight: FontWeight.bold,
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
