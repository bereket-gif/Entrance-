import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/quiz_provider.dart';

class DashboardScreen extends StatelessWidget {
  const DashboardScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<QuizProvider>(context);

    // Color Theme Coordinates
    const slateBg = Color(0xFF0A0E17);
    const slateSurface = Color(0xFF161F30);
    const slateBorder = Color(0xFF26354D);
    const blueAccent = Color(0xFF3B82F6);
    const textMuted = Color(0xFF8A9BAE);

    final subjects = [
      _SubjectData("Mathematics", Icons.calculate, Colors.orangeAccent),
      _SubjectData("Biology", Icons.biotech, Colors.lightGreenAccent),
      _SubjectData("Aptitude", Icons.psychology, Colors.purpleAccent),
      _SubjectData("English", Icons.translate, Colors.amberAccent),
      _SubjectData("Physics", Icons.bolt, Colors.cyanAccent),
      _SubjectData("Chemistry", Icons.science, Colors.tealAccent),
      _SubjectData("All Subjects", Icons.grid_view, Colors.pinkAccent),
    ];

    return Scaffold(
      backgroundColor: slateBg,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Banners
              const SizedBox(height: 10),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        "ETHIO MATRIC",
                        style: TextStyle(
                          fontSize: 26,
                          fontWeight: FontWeight.w900,
                          color: Colors.white,
                          letterSpacing: 1.2,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        "National Exam Prep Suite",
                        style: TextStyle(
                          fontSize: 14,
                          color: textMuted.withOpacity(0.8),
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ],
                  ),
                  Container(
                    decoration: BoxDecoration(
                      color: blueAccent.withOpacity(0.12),
                      borderRadius: BorderRadius.circular(10),
                      border: Border.all(color: blueAccent.withOpacity(0.25), width: 1.5),
                    ),
                    padding: const EdgeInsets.all(12),
                    child: const Icon(Icons.school, color: blueAccent, size: 28),
                  ),
                ],
              ),
              const SizedBox(height: 30),

              // Title: Drills
              const Text(
                "Subject Drills",
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                  letterSpacing: 0.5,
                ),
              ),
              const SizedBox(height: 14),

              // Sub list
              GridView.builder(
                shrinkWrap: true,
                physics: const NeverScrollableScrollPhysics(),
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  crossAxisSpacing: 12,
                  mainAxisSpacing: 12,
                  childAspectRatio: 1.45,
                ),
                itemCount: subjects.length,
                itemBuilder: (context, index) {
                  final sub = subjects[index];
                  return InkWell(
                    key: Key("subject_card_${sub.name.toLowerCase()}"),
                    onTap: () => provider.startQuiz(sub.name),
                    borderRadius: BorderRadius.circular(12),
                    child: Container(
                      decoration: BoxDecoration(
                        color: slateSurface,
                        borderRadius: BorderRadius.circular(12),
                        border: Border.all(color: slateBorder, width: 1),
                      ),
                      padding: const EdgeInsets.all(14),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Icon(sub.icon, color: sub.color, size: 26),
                          const Spacer(),
                          Text(
                            sub.name,
                            style: const TextStyle(
                              fontSize: 15,
                              fontWeight: FontWeight.bold,
                              color: Colors.white,
                            ),
                          ),
                          const SizedBox(height: 3),
                          Text(
                            sub.name == "All Subjects" ? "Mixed Quiz" : "Drills List",
                            style: const TextStyle(
                              fontSize: 11,
                              color: textMuted,
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),

              const SizedBox(height: 30),

              // Title: Logs and Bookmarks
              const Text(
                "Review & Records",
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                  letterSpacing: 0.5,
                ),
              ),
              const SizedBox(height: 14),

              // Saved Bookcards
              InkWell(
                key: const Key("dashboard_bookmarks_card"),
                onTap: () => provider.navigateTo(QuizScreenType.bookmarks),
                borderRadius: BorderRadius.circular(12),
                child: Container(
                  decoration: BoxDecoration(
                    color: slateSurface,
                    borderRadius: BorderRadius.circular(12),
                    border: Border.all(color: slateBorder, width: 1),
                  ),
                  padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
                  child: Row(
                    children: [
                      Container(
                        decoration: BoxDecoration(
                          color: const Color(0xFFFBBF24).withOpacity(0.12),
                          shape: BoxShape.circle,
                        ),
                        padding: const EdgeInsets.all(12),
                        child: const Icon(Icons.star, color: Color(0xFFFBBF24), size: 24),
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              "Saved Bookmarks",
                              style: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                              ),
                            ),
                            const SizedBox(height: 3),
                            Text(
                              "Review bookmarked questions",
                              style: TextStyle(
                                fontSize: 12,
                                color: textMuted.withOpacity(0.9),
                              ),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        decoration: BoxDecoration(
                          color: Colors.white10,
                          borderRadius: BorderRadius.circular(15),
                        ),
                        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                        child: Text(
                          "${provider.bookmarks.length}",
                          style: const TextStyle(
                            fontSize: 11,
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ),
                      const SizedBox(width: 6),
                      const Icon(Icons.arrow_forward_ios, size: 14, color: textMuted),
                    ],
                  ),
                ),
              ),

              const SizedBox(height: 12),

              // History Cards
              InkWell(
                key: const Key("dashboard_history_card"),
                onTap: () => provider.navigateTo(QuizScreenType.history),
                borderRadius: BorderRadius.circular(12),
                child: Container(
                  decoration: BoxDecoration(
                    color: slateSurface,
                    borderRadius: BorderRadius.circular(12),
                    border: Border.all(color: slateBorder, width: 1),
                  ),
                  padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
                  child: Row(
                    children: [
                      Container(
                        decoration: BoxDecoration(
                          color: const Color(0xFF10B981).withOpacity(0.12),
                          shape: BoxShape.circle,
                        ),
                        padding: const EdgeInsets.all(12),
                        child: const Icon(Icons.analytics_outlined, color: Color(0xFF10B981), size: 24),
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              "Performance Logs",
                              style: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                              ),
                            ),
                            const SizedBox(height: 3),
                            Text(
                              "View overall drills score statistics",
                              style: TextStyle(
                                fontSize: 12,
                                color: textMuted.withOpacity(0.9),
                              ),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        decoration: BoxDecoration(
                          color: Colors.white10,
                          borderRadius: BorderRadius.circular(15),
                        ),
                        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                        child: Text(
                          "${provider.historyList.length}",
                          style: const TextStyle(
                            fontSize: 11,
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ),
                      const SizedBox(width: 6),
                      const Icon(Icons.arrow_forward_ios, size: 14, color: textMuted),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _SubjectData {
  final String name;
  final IconData icon;
  final Color color;

  _SubjectData(this.name, this.icon, this.color);
}
