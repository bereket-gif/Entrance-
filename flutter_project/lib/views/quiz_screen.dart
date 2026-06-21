import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';
import '../viewmodels/quiz_viewmodel.dart';
import '../models/question.dart';

class QuizScreen extends StatelessWidget {
  const QuizScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final viewModel = context.watch<QuizViewModel>();
    if (viewModel.quizQuestions.isEmpty) {
      return const Center(child: CircularProgressIndicator());
    }

    final currentQ = viewModel.quizQuestions[viewModel.currentQuestionIndex];
    final isVerified = viewModel.isAnswerVerified;
    final selectedIdx = viewModel.selectedOptionIndex;

    return Column(
      children: [
        // Top Horizontal Dot Navigator
        _buildDotProgressNavigator(viewModel),

        // Scrollable Question Content
        Expanded(
          child: SingleChildScrollView(
            padding: const EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Subject Tag + Bookmark Button
                Row(
                  mainAxisAlignment: MainAxisAlignment.between,
                  children: [
                    Container(
                      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
                      decoration: BoxDecoration(
                        color: const Color(0xFF3B82F6).withOpacity(0.12),
                        borderRadius: BorderRadius.circular(12),
                        border: Border.all(color: const Color(0xFF3B82F6).withOpacity(0.2)),
                      ),
                      child: Text(
                        currentQ.subject.toUpperCase(),
                        style: GoogleFonts.plusJakartaSans(
                          color: const Color(0xFF3B82F6),
                          fontSize: 11,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    IconButton(
                      icon: Icon(
                        viewModel.isCurrentQuestionBookmarked
                            ? Icons.bookmark_rounded
                            : Icons.bookmark_border_rounded,
                        color: const Color(0xFF3B82F6),
                      ),
                      onPressed: viewModel.toggleBookmark,
                    ),
                  ],
                ),
                const SizedBox(height: 12),

                // Question Text Card
                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.all(20.0),
                  decoration: BoxDecoration(
                    color: const Color(0xFF1E293B),
                    borderRadius: BorderRadius.circular(20),
                    border: Border.all(color: const Color(0xFF334155)),
                  ),
                  child: Text(
                    currentQ.question,
                    style: GoogleFonts.plusJakartaSans(
                      color: Colors.white,
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      height: 1.5,
                    ),
                  ),
                ),
                const SizedBox(height: 20),

                // Option Choice Items
                _buildOptionTile(
                  index: 0,
                  label: "A",
                  text: currentQ.optionA,
                  viewModel: viewModel,
                  question: currentQ,
                ),
                _buildOptionTile(
                  index: 1,
                  label: "B",
                  text: currentQ.optionB,
                  viewModel: viewModel,
                  question: currentQ,
                ),
                _buildOptionTile(
                  index: 2,
                  label: "C",
                  text: currentQ.optionC,
                  viewModel: viewModel,
                  question: currentQ,
                ),
                _buildOptionTile(
                  index: 3,
                  label: "D",
                  text: currentQ.optionD,
                  viewModel: viewModel,
                  question: currentQ,
                ),
                const SizedBox(height: 24),

                // Verification Feedback / Explanatory Section
                if (isVerified) _buildExplanationCard(currentQ),
                const SizedBox(height: 16),
              ],
            ),
          ),
        ),

        // bottom action control bar
        _buildBottomActionBar(viewModel),
      ],
    );
  }

  Widget _buildDotProgressNavigator(QuizViewModel viewModel) {
    return Container(
      height: 54,
      decoration: const BoxDecoration(
        color: Color(0xFF1E293B),
        border: Border(
          bottom: BorderSide(color: Color(0xFF334155)),
        ),
      ),
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 12.0),
        itemCount: viewModel.quizQuestions.length,
        itemBuilder: (context, index) {
          final isCurrent = index == viewModel.currentQuestionIndex;
          final result = viewModel.questionResults[index];

          Color dotColor = const Color(0xFF334155); // unanswered
          IconData? icon;
          if (result == true) {
            dotColor = const Color(0xFF10B981); // correct
            icon = Icons.check;
          } else if (result == false) {
            dotColor = const Color(0xFFEF4444); // wrong
            icon = Icons.close;
          } else if (isCurrent) {
            dotColor = const Color(0xFF3B82F6); // current focus
          }

          return GestureDetector(
            onTap: () => viewModel.jumpToQuestion(index),
            child: Container(
              width: 30,
              height: 30,
              margin: const EdgeInsets.symmetric(horizontal: 4.0),
              decoration: BoxDecoration(
                color: dotColor,
                shape: BoxShape.circle,
                border: Border.all(
                  color: isCurrent ? Colors.white : Colors.transparent,
                  width: 1.5,
                ),
              ),
              alignment: Alignment.center,
              child: icon != null
                  ? Icon(icon, color: Colors.white, size: 12)
                  : Text(
                      "${index + 1}",
                      style: GoogleFonts.inter(
                        color: Colors.white,
                        fontSize: 10,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
            ),
          );
        },
      ),
    );
  }

  Widget _buildOptionTile({
    required int index,
    required String label,
    required String text,
    required QuizViewModel viewModel,
    required Question question,
  }) {
    final isSelected = viewModel.selectedOptionIndex == index;
    final isVerified = viewModel.isAnswerVerified;
    final isCorrectOption = question.correctOption == index;

    Color tileBgColor = const Color(0xFF1E293B);
    Color borderColor = const Color(0xFF334155);
    Color labelColor = const Color(0xFF94A3B8);
    Color textColor = Colors.white;

    if (isVerified) {
      if (isCorrectOption) {
        // correct option is always green once verified
        tileBgColor = const Color(0xFF10B981).withOpacity(0.1);
        borderColor = const Color(0xFF10B981);
        labelColor = const Color(0xFF10B981);
      } else if (isSelected) {
        // if user checked an incorrect option, it turns red
        tileBgColor = const Color(0xFFEF4444).withOpacity(0.1);
        borderColor = const Color(0xFFEF4444);
        labelColor = const Color(0xFFEF4444);
      }
    } else if (isSelected) {
      // highlighted focus under selection
      tileBgColor = const Color(0xFF3b82f6).withOpacity(0.1);
      borderColor = const Color(0xFF3B82F6);
      labelColor = const Color(0xFF3B82F6);
    }

    return Padding(
      padding: const EdgeInsets.only(bottom: 12.0),
      child: Material(
        color: Colors.transparent,
        child: InkWell(
          onTap: () => viewModel.selectOption(index),
          borderRadius: BorderRadius.circular(16),
          child: Container(
            padding: const EdgeInsets.all(16.0),
            decoration: BoxDecoration(
              color: tileBgColor,
              borderRadius: BorderRadius.circular(16),
              border: Border.all(color: borderColor, width: 1.5),
            ),
            child: Row(
              children: [
                // Circular Tag Indicator
                Container(
                  width: 30,
                  height: 30,
                  decoration: BoxDecoration(
                    color: isSelected || (isVerified && isCorrectOption)
                        ? Colors.transparent
                        : const Color(0xFF0F172A),
                    shape: BoxShape.circle,
                    border: Border.all(
                      color: labelColor,
                      width: 1.5,
                    ),
                  ),
                  alignment: Alignment.center,
                  child: Text(
                    label,
                    style: GoogleFonts.inter(
                      color: labelColor,
                      fontSize: 12,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(width: 14),
                Expanded(
                  child: Text(
                    text,
                    style: GoogleFonts.inter(
                      color: textColor,
                      fontSize: 14,
                    ),
                  ),
                ),
                if (isVerified && isCorrectOption)
                  const Icon(Icons.check_circle_rounded, color: Color(0xFF10B981), size: 20),
                if (isVerified && isSelected && !isCorrectOption)
                  const Icon(Icons.cancel_rounded, color: Color(0xFFEF4444), size: 20),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildExplanationCard(Question question) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(20.0),
      decoration: BoxDecoration(
        color: const Color(0xFF1E293B),
        borderRadius: BorderRadius.circular(20),
        border: Border.all(color: const Color(0xFF10B981).withOpacity(0.3), width: 1),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              const Icon(Icons.info_outline_rounded, color: Color(0xFF10B981), size: 20),
              const SizedBox(width: 8),
              Text(
                "Explanation",
                style: GoogleFonts.plusJakartaSans(
                  color: const Color(0xFF10B981),
                  fontSize: 14,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ],
          ),
          const SizedBox(height: 10),
          Text(
            question.explanation,
            style: GoogleFonts.inter(
              color: const Color(0xFF94A3B8),
              fontSize: 13,
              height: 1.5,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildBottomActionBar(QuizViewModel viewModel) {
    final isSelected = viewModel.selectedOptionIndex != null;
    final isVerified = viewModel.isAnswerVerified;

    return Container(
      padding: const EdgeInsets.all(16),
      decoration: const BoxDecoration(
        color: Color(0xFF1E293B),
        border: Border(
          top: BorderSide(color: Color(0xFF334155)),
        ),
      ),
      child: Row(
        children: [
          Text(
            "Q${viewModel.currentQuestionIndex + 1} of ${viewModel.quizQuestions.length}",
            style: GoogleFonts.inter(
              color: const Color(0xFF94A3B8),
              fontSize: 14,
            ),
          ),
          const Spacer(),
          if (!isVerified)
            ElevatedButton(
              onPressed: isSelected ? viewModel.verifyAnswer : null,
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF3B82F6),
                disabledBackgroundColor: const Color(0xFF334155),
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                shape: RoundedCornerShape(12),
              ),
              child: Text(
                "Verify Answer",
                style: GoogleFonts.plusJakartaSans(fontWeight: FontWeight.bold),
              ),
            )
          else
            ElevatedButton(
              onPressed: viewModel.nextQuestion,
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF10B981),
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                shape: RoundedCornerShape(12),
              ),
              child: Text(
                viewModel.currentQuestionIndex + 1 == viewModel.quizQuestions.length
                    ? "Finish exam"
                    : "Next Question",
                style: GoogleFonts.plusJakartaSans(fontWeight: FontWeight.bold),
              ),
            )
        ],
      ),
    );
  }

  RoundedRectangleBorder RoundedCornerShape(double radius) {
    return RoundedRectangleBorder(borderRadius: BorderRadius.circular(radius));
  }
}
