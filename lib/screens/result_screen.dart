import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/quiz_provider.dart';

class ResultScreen extends StatefulWidget {
  const ResultScreen({super.key});

  @override
  State<ResultScreen> createState() => _ResultScreenState();
}

class _ResultScreenState extends State<ResultScreen> {
  final Map<int, bool> _expandedMap = {};

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<QuizProvider>(context);
    final questions = provider.quizQuestions;
    final results = provider.questionResults;
    final score = provider.score;

    final total = questions.length;
    final accuracy = total > 0 ? (score * 100) ~/ total : 0;

    // Formatting time MM:SS
    String formatTime(int seconds) {
      final m = seconds ~/ 60;
      final s = seconds % 60;
      return "${m.toString().padLeft(2, '0')}:${s.toString().padLeft(2, '0')}";
    }

    // Colors
    const slateBg = Color(0xFF0A0E17);
    const slateSurface = Color(0xFF161F30);
    const slateBorder = Color(0xFF26354D);
    const blueAccent = Color(0xFF3B82F6);
    const emeraldAccent = Color(0xFF10B981);
    const redAccent = Color(0xFFEF4444);
    const textMuted = Color(0xFF8A9BAE);

    return Scaffold(
      backgroundColor: slateBg,
      body: SafeArea(
        child: Column(
          children: [
            // Top App Bar
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 14.0, vertical: 12.0),
              child: Row(
                children: [
                  const Icon(Icons.stars, color: Colors.amber, size: 28),
                  const SizedBox(width: 12),
                  const Text(
                    "DRILL SHEET REPORT",
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.black,
                      color: Colors.white,
                      letterSpacing: 0.8,
                    ),
                  ),
                  const Spacer(),
                  IconButton(
                    onPressed: () => provider.resetApp(),
                    icon: const Icon(Icons.close, color: Colors.white, size: 22),
                  ),
                ],
              ),
            ),

            Expanded(
              child: SingleChildScrollView(
                padding: const EdgeInsets.symmetric(horizontal: 18.0),
                child: Column(
                  children: [
                    const SizedBox(height: 10),

                    // Beautiful Custom Dashboard Accuracy Ring
                    Container(
                      decoration: BoxDecoration(
                        color: slateSurface,
                        border: Border.all(color: slateBorder),
                        borderRadius: BorderRadius.circular(20),
                      ),
                      padding: const EdgeInsets.symmetric(vertical: 24, horizontal: 20),
                      child: Column(
                        children: [
                          Center(
                            child: SizedBox(
                              width: 125,
                              height: 125,
                              child: Stack(
                                fit: StackFit.expand,
                                children: [
                                  CircularProgressIndicator(
                                    value: accuracy / 100,
                                    strokeWidth: 10,
                                    backgroundColor: slateBg,
                                    color: accuracy >= 80 ? emeraldAccent : accuracy >= 50 ? blueAccent : redAccent,
                                  ),
                                  Center(
                                    child: Column(
                                      mainAxisAlignment: MainAxisAlignment.center,
                                      children: [
                                        Text(
                                          "$accuracy%",
                                          style: const TextStyle(
                                            fontSize: 32,
                                            fontWeight: FontWeight.black,
                                            color: Colors.white,
                                          ),
                                        ),
                                        const Text(
                                          "Accuracy",
                                          style: TextStyle(
                                            fontSize: 11,
                                            fontWeight: FontWeight.bold,
                                            color: textMuted,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          const SizedBox(height: 20),
                          Text(
                            "$score / $total Solved Correctly",
                            style: const TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              color: Colors.white,
                            ),
                          ),
                          const SizedBox(height: 6),
                          Text(
                            accuracy >= 80
                                ? "Excellent! You are ready for exams."
                                : accuracy >= 50
                                    ? "Good job! Focus on weaker topics."
                                    : "Needs practice! Keep practicing.",
                            style: const TextStyle(
                              fontSize: 13,
                              color: textMuted,
                            ),
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 16),

                    // Sub cards: Time and Subjects
                    Row(
                      children: [
                        Expanded(
                          child: Container(
                            decoration: BoxDecoration(
                              color: slateSurface,
                              border: Border.all(color: slateBorder),
                              borderRadius: BorderRadius.circular(12),
                            ),
                            padding: const EdgeInsets.all(14.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                const Text(
                                  "ELAPSED DURATION",
                                  style: TextStyle(fontSize: 10, color: textMuted, fontWeight: FontWeight.bold),
                                ),
                                const SizedBox(height: 5),
                                Text(
                                  formatTime(provider.elapsedSeconds),
                                  style: const TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.w900,
                                    color: Colors.white,
                                    fontFamily: 'monospace',
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(width: 12),
                        Expanded(
                          child: Container(
                            decoration: BoxDecoration(
                              color: slateSurface,
                              border: Border.all(color: slateBorder),
                              borderRadius: BorderRadius.circular(12),
                            ),
                            padding: const EdgeInsets.all(14.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                const Text(
                                  "ACADEMIC SUBJECT",
                                  style: TextStyle(fontSize: 10, color: textMuted, fontWeight: FontWeight.bold),
                                ),
                                const SizedBox(height: 5),
                                Text(
                                  provider.selectedSubject,
                                  style: const TextStyle(
                                    fontSize: 15,
                                    fontWeight: FontWeight.w900,
                                    color: Colors.white,
                                    overflow: TextOverflow.ellipsis,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 24),

                    // Detailed breakdown header
                    const Align(
                      alignment: Alignment.centerLeft,
                      child: Text(
                        "Detailed Session Review",
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                    ),
                    const SizedBox(height: 12),

                    // Multi Expansion card lists
                    ListView.builder(
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: questions.length,
                      itemBuilder: (context, i) {
                        final question = questions[i];
                        final isCorrect = results[i] == true;
                        final isExpanded = _expandedMap[i] ?? false;

                        return Container(
                          margin: const EdgeInsets.only(bottom: 12.0),
                          decoration: BoxDecoration(
                            color: slateSurface,
                            border: Border.all(color: slateBorder),
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              InkWell(
                                onTap: () {
                                  setState(() {
                                    _expandedMap[i] = !isExpanded;
                                  });
                                },
                                borderRadius: BorderRadius.circular(12),
                                child: Padding(
                                  padding: const EdgeInsets.all(14.0),
                                  child: Row(
                                    children: [
                                      Container(
                                        decoration: BoxDecoration(
                                          color: isCorrect ? emeraldAccent.withOpacity(0.1) : redAccent.withOpacity(0.1),
                                          shape: BoxShape.circle,
                                        ),
                                        padding: const EdgeInsets.all(6),
                                        child: Icon(
                                          isCorrect ? Icons.check : Icons.close,
                                          color: isCorrect ? emeraldAccent : redAccent,
                                          size: 16,
                                        ),
                                      ),
                                      const SizedBox(width: 14),
                                      Expanded(
                                        child: Text(
                                          question.question,
                                          maxLines: 1,
                                          overflow: TextOverflow.ellipsis,
                                          style: const TextStyle(
                                            fontSize: 14.5,
                                            fontWeight: FontWeight.bold,
                                            color: Colors.white,
                                          ),
                                        ),
                                      ),
                                      const SizedBox(width: 8),
                                      Icon(
                                        isExpanded ? Icons.expand_less : Icons.expand_more,
                                        color: textMuted,
                                        size: 20,
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              if (isExpanded) ...[
                                const Divider(color: slateBorder, height: 1),
                                Padding(
                                  padding: const EdgeInsets.all(16.0),
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text(
                                        question.question,
                                        style: const TextStyle(
                                          fontSize: 14,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                          height: 1.4,
                                        ),
                                      ),
                                      const SizedBox(height: 14),
                                      const Text(
                                        "Correct Option",
                                        style: TextStyle(
                                          fontSize: 11,
                                          fontWeight: FontWeight.bold,
                                          color: emeraldAccent,
                                        ),
                                      ),
                                      const SizedBox(height: 2),
                                      Text(
                                        question.options[question.correct],
                                        style: const TextStyle(fontSize: 13.5, color: Colors.white),
                                      ),
                                      const SizedBox(height: 12),
                                      const Text(
                                        "Step Explanation",
                                        style: TextStyle(
                                          fontSize: 11,
                                          fontWeight: FontWeight.bold,
                                          color: blueAccent,
                                        ),
                                      ),
                                      const SizedBox(height: 4),
                                      Text(
                                        question.explanation,
                                        style: const TextStyle(
                                          fontSize: 13,
                                          color: textMuted,
                                          height: 1.4,
                                        ),
                                      ),
                                    ],
                                  ),
                                )
                              ],
                            ],
                          ),
                        );
                      },
                    ),
                    const SizedBox(height: 20),
                  ],
                ),
              ),
            ),

            // Return to dashboard bottom bar
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: SizedBox(
                width: double.infinity,
                height: 52,
                child: ElevatedButton(
                  onPressed: () => provider.resetApp(),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: blueAccent,
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                    elevation: 0,
                  ),
                  child: const Text(
                    "CONCLUDE & RETURN",
                    style: TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
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
