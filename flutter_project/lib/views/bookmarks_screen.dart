import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';
import '../viewmodels/quiz_viewmodel.dart';
import '../models/bookmark.dart';

class BookmarksScreen extends StatefulWidget {
  const BookmarksScreen({super.key});

  @override
  State<BookmarksScreen> createState() => _BookmarksScreenState();
}

class _BookmarksScreenState extends State<BookmarksScreen> {
  final Set<String> _expandedQuestions = {};

  @override
  Widget build(BuildContext context) {
    final viewModel = context.watch<QuizViewModel>();
    final list = viewModel.bookmarks;

    if (list.isEmpty) {
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
                  Icons.bookmark_outline_rounded,
                  color: Color(0xFF94A3B8),
                  size: 48,
                ),
              ),
              const SizedBox(height: 20),
              Text(
                "No Saved Bookmarks",
                style: GoogleFonts.plusJakartaSans(
                  color: Colors.white,
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              Text(
                "During a quiz, tap the bookmark icon on hard or interesting questions to save them here for offline study.",
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

    return ListView.builder(
      padding: const EdgeInsets.all(20.0),
      itemCount: list.length,
      itemBuilder: (context, index) {
        final bookmark = list[index];
        final isExpanded = _expandedQuestions.contains(bookmark.questionText);

        return Card(
          color: const Color(0xFF1E293B),
          margin: const EdgeInsets.only(bottom: 16.0),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(16.0),
            side: const BorderSide(color: Color(0xFF334155)),
          ),
          clipBehavior: Clip.antiAlias,
          child: InkWell(
            onTap: () {
              setState(() {
                if (isExpanded) {
                  _expandedQuestions.remove(bookmark.questionText);
                } else {
                  _expandedQuestions.add(bookmark.questionText);
                }
              });
            },
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Top header: Subject + Delete button
                Padding(
                  padding: const EdgeInsets.fromLTRB(16, 16, 8, 8),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.between,
                    children: [
                      Container(
                        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                        decoration: BoxDecoration(
                          color: const Color(0xFF3B82F6).withOpacity(0.12),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Text(
                          bookmark.subject.toUpperCase(),
                          style: GoogleFonts.plusJakartaSans(
                            color: const Color(0xFF3B82F6),
                            fontSize: 10,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                      IconButton(
                        icon: const Icon(Icons.delete_sweep_rounded, color: Color(0xFFEF4444), size: 20),
                        onPressed: () => viewModel.deleteBookmarkDirectly(bookmark.questionText),
                      ),
                    ],
                  ),
                ),

                // Question Title
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 4.0),
                  child: Text(
                    bookmark.questionText,
                    style: GoogleFonts.plusJakartaSans(
                      color: Colors.white,
                      fontSize: 14,
                      fontWeight: FontWeight.bold,
                      height: 1.4,
                    ),
                  ),
                ),

                const SizedBox(height: 8),

                // Expansion content: Options list and explanation
                if (isExpanded) ...[
                  const Divider(color: Color(0xFF334155), height: 1),
                  Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        _buildOptionRow("A", bookmark.optionA, bookmark.correctOption == 0),
                        _buildOptionRow("B", bookmark.optionB, bookmark.correctOption == 1),
                        _buildOptionRow("C", bookmark.optionC, bookmark.correctOption == 2),
                        _buildOptionRow("D", bookmark.optionD, bookmark.correctOption == 3),
                        const SizedBox(height: 16),
                        Container(
                          width: double.infinity,
                          padding: const EdgeInsets.all(14.0),
                          decoration: BoxDecoration(
                            color: const Color(0xFF0F172A),
                            borderRadius: BorderRadius.circular(12),
                            border: Border.all(color: const Color(0xFF10B981).withOpacity(0.2)),
                          ),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                "CORRECT ANSWER EXPLANATION",
                                style: GoogleFonts.plusJakartaSans(
                                  color: const Color(0xFF10B981),
                                  fontSize: 10,
                                  fontWeight: FontWeight.bold,
                                  letterSpacing: 0.5,
                                ),
                              ),
                              const SizedBox(height: 6),
                              Text(
                                bookmark.explanation,
                                style: GoogleFonts.inter(
                                  color: const Color(0xFF94A3B8),
                                  fontSize: 12,
                                  height: 1.4,
                                ),
                              ),
                            ],
                          ),
                        )
                      ],
                    ),
                  )
                ] else ...[
                  Padding(
                    padding: const EdgeInsets.only(left: 16.0, bottom: 12.0),
                    child: Row(
                      children: [
                        const Icon(Icons.arrow_drop_down_circle_outlined, size: 14, color: Color(0xFF94A3B8)),
                        const SizedBox(width: 6),
                        Text(
                          "Tap to inspect choices & explanation",
                          style: GoogleFonts.inter(
                            color: const Color(0xFF94A3B8),
                            fontSize: 11,
                          ),
                        ),
                      ],
                    ),
                  )
                ]
              ],
            ),
          ),
        );
      },
    );
  }

  Widget _buildOptionRow(String label, String text, bool isCorrect) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8.0),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
            decoration: BoxDecoration(
              color: isCorrect ? const Color(0xFF10B981).withOpacity(0.15) : const Color(0xFF0F172A),
              borderRadius: BorderRadius.circular(4),
              border: Border.all(
                color: isCorrect ? const Color(0xFF10B981) : const Color(0xFF334155),
              ),
            ),
            child: Text(
              label,
              style: GoogleFonts.inter(
                color: isCorrect ? const Color(0xFF10B981) : const Color(0xFF94A3B8),
                fontSize: 10,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          const SizedBox(width: 10),
          Expanded(
            child: Text(
              text,
              style: GoogleFonts.inter(
                color: isCorrect ? const Color(0xFF10B981) : Colors.white,
                fontSize: 13,
                fontWeight: isCorrect ? FontWeight.bold : FontWeight.normal,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
