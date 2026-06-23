import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/quiz_provider.dart';

class QuizScreen extends StatelessWidget {
  const QuizScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<QuizProvider>(context);
    final questions = provider.quizQuestions;
    final index = provider.currentQuestionIndex;

    // Guard if empty
    if (questions.isEmpty || index >= questions.length) {
      return const Scaffold(
        backgroundColor: Color(0xFF0A0E17),
        body: Center(
          child: CircularProgressIndicator(color: Color(0xFF3B82F6)),
        ),
      );
    }

    final question = questions[index];

    // Color Setup
    const slateBg = Color(0xFF0A0E17);
    const slateSurface = Color(0xFF161F30);
    const slateBorder = Color(0xFF26354D);
    const blueAccent = Color(0xFF3B82F6);
    const emeraldAccent = Color(0xFF10B981);
    const redAccent = Color(0xFFEF4444);
    const orangeAccent = Color(0xFFF59E0B);
    const textMuted = Color(0xFF8A9BAE);

    // Formatted time MM:SS
    String formatTime(int seconds) {
      final m = seconds ~/ 60;
      final s = seconds % 60;
      return "${m.toString().padLeft(2, '0')}:${s.toString().padLeft(2, '0')}";
    }

    return Scaffold(
      backgroundColor: slateBg,
      body: SafeArea(
        child: Column(
          children: [
            // Header controls
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 14.0, vertical: 8.0),
              child: Row(
                children: [
                  IconButton(
                    key: const Key("quit_button"),
                    icon: const Icon(Icons.arrow_back, color: Colors.white, size: 24),
                    onPressed: () => provider.resetApp(),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          provider.selectedSubject.toUpperCase(),
                          style: const TextStyle(
                            fontSize: 13,
                            fontWeight: FontWeight.bold,
                            color: blueAccent,
                            letterSpacing: 0.5,
                          ),
                        ),
                        const SizedBox(height: 2),
                        Text(
                          "Question ${index + 1} of ${questions.length}",
                          style: const TextStyle(fontSize: 11, color: textMuted),
                        ),
                      ],
                    ),
                  ),
                  Container(
                    decoration: BoxDecoration(
                      color: Colors.white.withOpacity(0.06),
                      borderRadius: BorderRadius.circular(20),
                    ),
                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                    child: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        const Icon(Icons.alarm, color: orangeAccent, size: 16),
                        const SizedBox(width: 6),
                        Text(
                          formatTime(provider.elapsedSeconds),
                          style: const TextStyle(
                            fontSize: 12,
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                            fontFamily: 'monospace',
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(width: 4),
                  IconButton(
                    key: const Key("bookmark_toggle"),
                    icon: Icon(
                      provider.isCurrentQuestionBookmarked ? Icons.star : Icons.star_border,
                      color: provider.isCurrentQuestionBookmarked ? Colors.amber : Colors.white,
                      size: 24,
                    ),
                    onPressed: () => provider.toggleBookmark(),
                  ),
                ],
              ),
            ),

            // Continuous Linear Progress Line
            LinearProgressIndicator(
              value: (index + 1) / questions.length,
              backgroundColor: slateBorder,
              color: blueAccent,
              minHeight: 3.5,
            ),

            // Question dot browser view
            Container(
              height: 48,
              padding: const EdgeInsets.symmetric(vertical: 6.0),
              decoration: BoxDecoration(
                color: slateSurface.withOpacity(0.5),
                border: const Border(
                  bottom: BorderSide(color: slateBorder, width: 0.8),
                ),
              ),
              child: ListView.builder(
                scrollDirection: Axis.horizontal,
                padding: const EdgeInsets.symmetric(horizontal: 16.0),
                itemCount: questions.length,
                itemBuilder: (context, i) {
                  final result = provider.questionResults[i];
                  final isCurrent = i == index;

                  Color dotColor = Colors.transparent;
                  Color borderColor = slateBorder;
                  IconData? resultIcon;

                  if (result == true) {
                    dotColor = emeraldAccent.withOpacity(0.12);
                    borderColor = emeraldAccent;
                    resultIcon = Icons.check;
                  } else if (result == false) {
                    dotColor = redAccent.withOpacity(0.12);
                    borderColor = redAccent;
                    resultIcon = Icons.close;
                  }

                  if (isCurrent) {
                    borderColor = blueAccent;
                  }

                  return InkWell(
                    onTap: () => provider.jumpToQuestion(i),
                    borderRadius: BorderRadius.circular(16),
                    child: Container(
                      width: 32,
                      height: 32,
                      margin: const EdgeInsets.only(right: 8.0),
                      decoration: BoxDecoration(
                        color: dotColor,
                        shape: BoxShape.circle,
                        border: Border.all(color: borderColor, width: isCurrent ? 2.5 : 1),
                      ),
                      alignment: Alignment.Center,
                      child: resultIcon != null
                          ? Icon(resultIcon, size: 14, color: result == true ? emeraldAccent : redAccent)
                          : Text(
                              "${i + 1}",
                              style: TextStyle(
                                fontSize: 11,
                                fontWeight: FontWeight.bold,
                                color: isCurrent ? Colors.white : textMuted,
                              ),
                            ),
                    ),
                  );
                },
              ),
            ),

            // Scrollable Active body
            Expanded(
              child: SingleChildScrollView(
                padding: const EdgeInsets.all(18.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Question text box
                    Container(
                      decoration: BoxDecoration(
                        color: slateSurface,
                        border: Border.all(color: slateBorder),
                        borderRadius: BorderRadius.circular(12),
                      ),
                      padding: const EdgeInsets.all(16.0),
                      width: double.infinity,
                      child: Text(
                        question.question,
                        style: const TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                          height: 1.45,
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),

                    // Options Container
                    ListView.builder(
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: question.options.length,
                      itemBuilder: (context, optIdx) {
                        final optionText = question.options[optIdx];
                        final isSelected = provider.selectedOptionIndex == optIdx;
                        final isVerified = provider.isAnswerVerified;
                        final isCorrect = optIdx == question.correct;

                        Color opBorderColor = slateBorder;
                        Color opBgColor = Colors.transparent;
                        Widget trailingIcon = const SizedBox();

                        if (isVerified) {
                          if (isCorrect) {
                            opBorderColor = emeraldAccent;
                            opBgColor = emeraldAccent.withOpacity(0.08);
                            trailingIcon = const Icon(Icons.check_circle, color: emeraldAccent, size: 20);
                          } else if (isSelected && !isCorrect) {
                            opBorderColor = redAccent;
                            opBgColor = redAccent.withOpacity(0.08);
                            trailingIcon = const Icon(Icons.cancel, color: redAccent, size: 20);
                          }
                        } else {
                          if (isSelected) {
                            opBorderColor = blueAccent;
                            opBgColor = blueAccent.withOpacity(0.08);
                          }
                        }

                        // Prefix labels (A, B, C, D)
                        final optLabel = String.fromCharCode(65 + optIdx);

                        return Container(
                          margin: const EdgeInsets.only(bottom: 12.0),
                          child: InkWell(
                            onTap: () => provider.selectOption(optIdx),
                            borderRadius: BorderRadius.circular(10),
                            child: Container(
                              decoration: BoxDecoration(
                                color: opBgColor,
                                border: Border.all(color: opBorderColor, width: isSelected || (isVerified && isCorrect) ? 1.8 : 1),
                                borderRadius: BorderRadius.circular(10),
                              ),
                              padding: const EdgeInsets.symmetric(horizontal: 14.0, vertical: 15.0),
                              child: Row(
                                children: [
                                  Container(
                                    decoration: BoxDecoration(
                                      color: isSelected ? blueAccent : slateBg,
                                      borderRadius: BorderRadius.circular(6),
                                      border: Border.all(color: isSelected ? blueAccent : slateBorder),
                                    ),
                                    width: 28,
                                    height: 28,
                                    alignment: Alignment.Center,
                                    child: Text(
                                      optLabel,
                                      style: TextStyle(
                                        fontSize: 13,
                                        fontWeight: FontWeight.bold,
                                        color: isSelected ? Colors.white : textMuted,
                                      ),
                                    ),
                                  ),
                                  const SizedBox(width: 14),
                                  Expanded(
                                    child: Text(
                                      optionText,
                                      style: const TextStyle(
                                        fontSize: 14.5,
                                        color: Colors.white,
                                        fontWeight: FontWeight.w500,
                                      ),
                                    ),
                                  ),
                                  trailingIcon,
                                ],
                              ),
                            ),
                          ),
                        );
                      },
                    ),

                    // Explanation Screen (Display when answer is verified)
                    if (provider.isAnswerVerified) ...[
                      const SizedBox(height: 18),
                      Container(
                        decoration: BoxDecoration(
                          color: slateSurface,
                          border: Border.all(color: slateBorder),
                          borderRadius: BorderRadius.circular(12),
                        ),
                        padding: const EdgeInsets.all(16.0),
                        width: double.infinity,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Row(
                              children: [
                                Icon(Icons.assignment_turned_in, color: emeraldAccent, size: 18),
                                SizedBox(width: 8),
                                Text(
                                  "Correct Solution",
                                  style: TextStyle(
                                    fontSize: 14,
                                    fontWeight: FontWeight.bold,
                                    color: emeraldAccent,
                                  ),
                                ),
                              ],
                            ),
                            const SizedBox(height: 6),
                            Text(
                              question.options[question.correct],
                              style: const TextStyle(
                                fontSize: 14,
                                color: Colors.white,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            const SizedBox(height: 14),
                            const Divider(color: slateBorder),
                            const SizedBox(height: 10),
                            const Row(
                              children: [
                                Icon(Icons.lightbulb, color: blueAccent, size: 18),
                                SizedBox(width: 8),
                                Text(
                                  "Step Explanation",
                                  style: TextStyle(
                                    fontSize: 14,
                                    fontWeight: FontWeight.bold,
                                    color: blueAccent,
                                  ),
                                ),
                              ],
                            ),
                            const SizedBox(height: 6),
                            Text(
                              question.explanation,
                              style: const TextStyle(
                                fontSize: 13.5,
                                color: textMuted,
                                height: 1.45,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ],
                ),
              ),
            ),

            // Navigation CTA button bar
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: SizedBox(
                width: double.infinity,
                height: 52,
                child: ElevatedButton(
                  key: const Key("action_button"),
                  onPressed: provider.selectedOptionIndex == null
                      ? null
                      : () {
                          if (provider.isAnswerVerified) {
                            provider.nextQuestion();
                          } else {
                            provider.verifyAnswer();
                          }
                        },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: provider.isAnswerVerified ? emeraldAccent : blueAccent,
                    disabledBackgroundColor: slateSurface,
                    shape: RoundedCornerShape(12),
                    elevation: 0,
                  ),
                  child: Text(
                    provider.isAnswerVerified
                        ? (index + 1 == questions.length ? "SUMBIT RESULTS" : "NEXT QUESTION")
                        : "VERIFY ANSWER",
                    style: TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.bold,
                      color: provider.selectedOptionIndex == null ? textMuted : Colors.white,
                      letterSpacing: 0.5,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
extension RoundedCornerShape on OutlinedBorder {
  static RoundedRectangleBorder RoundedCornerShape(double radius) {
    return RoundedRectangleBorder(borderRadius: BorderRadius.circular(radius));
  }
}
