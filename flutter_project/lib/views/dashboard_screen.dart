import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';
import '../viewmodels/quiz_viewmodel.dart';

class DashboardScreen extends StatelessWidget {
  const DashboardScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final viewModel = context.watch<QuizViewModel>();
    final bookmarksCount = viewModel.bookmarks.length;
    final historyCount = viewModel.historyList.length;

    return SingleChildScrollView(
      padding: const EdgeInsets.all(20.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Hero Title Banner Card
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(24.0),
            decoration: BoxDecoration(
              gradient: const LinearGradient(
                colors: [Color(0xFF1E293B), Color(0xFF0F172A)],
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
              ),
              borderRadius: BorderRadius.circular(24.0),
              border: Border.all(color: const Color(0xFF334155), width: 1.5),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  padding: const EdgeInsets.horizontal(12.0,),
                  decoration: BoxDecoration(
                    color: const Color(0xFF10B981).withOpacity(0.15),
                    borderRadius: BorderRadius.circular(20),
                    border: Border.all(color: const Color(0xFF10B981).withOpacity(0.3)),
                  ),
                  child: Text(
                    "ETHIOPIAN UNIVERSITY MATRIC",
                    style: GoogleFonts.plusJakartaSans(
                      color: const Color(0xFF10B981),
                      fontSize: 10,
                      fontWeight: FontWeight.bold,
                      letterSpacing: 1.2,
                    ),
                  ),
                ),
                const SizedBox(height: 16),
                Text(
                  "Excel Your Entrance Exams",
                  style: GoogleFonts.plusJakartaSans(
                    color: Colors.white,
                    fontSize: 24,
                    fontWeight: FontWeight.extrabold,
                  ),
                ),
                const SizedBox(height: 8),
                Text(
                  "Practice with 100 complete, categorized, and shuffled prep questions per subject with explanations.",
                  style: GoogleFonts.inter(
                    color: const Color(0xFF94A3B8),
                    fontSize: 14,
                    height: 1.5,
                  ),
                ),
              ],
            ),
          ),
          const SizedBox(height: 28),

          // Practice statistics counters
          Row(
            children: [
              Expanded(
                child: _buildStatCard(
                  context,
                  title: "Bookmarks",
                  subtitle: "$bookmarksCount Saved",
                  icon: Icons.bookmark_added_rounded,
                  color: const Color(0xFF3B82F6),
                  onTap: () => viewModel.navigateTo(Screen.bookmarks),
                ),
              ),
              const SizedBox(width: 16),
              Expanded(
                child: _buildStatCard(
                  context,
                  title: "History Logs",
                  subtitle: "$historyCount Finished",
                  icon: Icons.analytics_rounded,
                  color: const Color(0xFF10B981),
                  onTap: () => viewModel.navigateTo(Screen.history),
                ),
              ),
            ],
          ),
          const SizedBox(height: 28),

          // Mixed exam starter card
          _buildMixedExamCard(viewModel),
          const SizedBox(height: 28),

          // Categories Title
          Text(
            "Select Prep Subject",
            style: GoogleFonts.plusJakartaSans(
              color: Colors.white,
              fontSize: 18,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 16),

          // Grid list of individual subjects
          GridView.count(
            crossAxisCount: 2,
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            crossAxisSpacing: 16,
            mainAxisSpacing: 16,
            childAspectRatio: 1.3,
            children: [
              _buildSubjectTile(
                title: "Mathematics",
                subtitle: "Calculus & Algebra",
                icon: Icons.functions_rounded,
                color: const Color(0xFF3B82F6),
                onTap: () => viewModel.startQuiz("Mathematics"),
              ),
              _buildSubjectTile(
                title: "Biology",
                subtitle: "Cytology & Genetics",
                icon: Icons.biotech_rounded,
                color: const Color(0xFF10B981),
                onTap: () => viewModel.startQuiz("Biology"),
              ),
              _buildSubjectTile(
                title: "Aptitude",
                subtitle: "Logic & Critical",
                icon: Icons.psychology_rounded,
                color: const Color(0xFFF59E0B),
                onTap: () => viewModel.startQuiz("Aptitude"),
              ),
              _buildSubjectTile(
                title: "English",
                subtitle: "Grammar & Vocabulary",
                icon: Icons.translate_rounded,
                color: const Color(0xFFEC4899),
                onTap: () => viewModel.startQuiz("English"),
              ),
              _buildSubjectTile(
                title: "Physics",
                subtitle: "Mechanics & Optics",
                icon: Icons.bolt_rounded,
                color: const Color(0xFF8B5CF6),
                onTap: () => viewModel.startQuiz("Physics"),
              ),
              _buildSubjectTile(
                title: "Chemistry",
                subtitle: "Organic & Physical",
                icon: Icons.science_rounded,
                color: const Color(0xFF14B8A6),
                onTap: () => viewModel.startQuiz("Chemistry"),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildStatCard(
    BuildContext context, {
    required String title,
    required String subtitle,
    required IconData icon,
    required Color color,
    required VoidCallback onTap,
  }) {
    return InkWell(
      onTap: onTap,
      borderRadius: BorderRadius.circular(16.0),
      child: Container(
        padding: const EdgeInsets.all(16.0),
        decoration: BoxDecoration(
          color: const Color(0xFF1E293B),
          borderRadius: BorderRadius.circular(16.0),
          border: Border.all(color: const Color(0xFF334155)),
        ),
        child: Row(
          children: [
            Container(
              padding: const EdgeInsets.all(10.0),
              decoration: BoxDecoration(
                color: color.withOpacity(0.1),
                borderRadius: BorderRadius.circular(12),
              ),
              child: Icon(icon, color: color, size: 24),
            ),
            const SizedBox(width: 12),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    title,
                    style: GoogleFonts.plusJakartaSans(
                      color: Colors.white,
                      fontSize: 14,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    subtitle,
                    style: GoogleFonts.inter(
                      color: const Color(0xFF94A3B8),
                      fontSize: 12,
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget _buildMixedExamCard(QuizViewModel viewModel) {
    return Container(
      decoration: BoxDecoration(
        color: const Color(0xFF1E293B),
        borderRadius: BorderRadius.circular(20),
        border: Border.all(color: const Color(0xFF334155), width: 1.5),
      ),
      child: InkWell(
        onTap: () => viewModel.startQuiz("All Subjects"),
        borderRadius: BorderRadius.circular(20),
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Row(
            children: [
              Container(
                padding: const EdgeInsets.all(14),
                decoration: BoxDecoration(
                  gradient: const LinearGradient(
                    colors: [Color(0xFF8B5CF6), Color(0xFF3B82F6)],
                  ),
                  borderRadius: BorderRadius.circular(16),
                ),
                child: const Icon(
                  Icons.quiz_rounded,
                  color: Colors.white,
                  size: 28,
                ),
              ),
              const SizedBox(width: 16),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      "Mixed Exam Simulator",
                      style: GoogleFonts.plusJakartaSans(
                        color: Colors.white,
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      "100 Mixed Shuffled Exam Questions",
                      style: GoogleFonts.inter(
                        color: const Color(0xFF94A3B8),
                        fontSize: 12,
                      ),
                    ),
                  ],
                ),
              ),
              const Icon(
                Icons.arrow_forward_ios_rounded,
                color: Color(0xFF94A3B8),
                size: 16,
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildSubjectTile({
    required String title,
    required String subtitle,
    required IconData icon,
    required Color color,
    required VoidCallback onTap,
  }) {
    return Container(
      decoration: BoxDecoration(
        color: const Color(0xFF1E293B),
        borderRadius: BorderRadius.circular(18),
        border: Border.all(color: const Color(0xFF334155)),
      ),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(18),
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: color.withOpacity(0.1),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Icon(icon, color: color, size: 24),
              ),
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    title,
                    style: GoogleFonts.plusJakartaSans(
                      color: Colors.white,
                      fontSize: 14,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 2),
                  Text(
                    subtitle,
                    style: GoogleFonts.inter(
                      color: const Color(0xFF94A3B8),
                      fontSize: 11,
                    ),
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
