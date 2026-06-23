import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/quiz_provider.dart';

class BookmarksScreen extends StatefulWidget {
  const BookmarksScreen({super.key});

  @override
  State<BookmarksScreen> createState() => _BookmarksScreenState();
}

class _BookmarksScreenState extends State<BookmarksScreen> {
  final Map<String, bool> _expandMap = {};

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<QuizProvider>(context);
    final bookmarks = provider.bookmarks;

    // Color Theme Coordinates
    const slateBg = Color(0xFF0A0E17);
    const slateSurface = Color(0xFF161F30);
    const slateBorder = Color(0xFF26354D);
    const blueAccent = Color(0xFF3B82F6);
    const emeraldAccent = Color(0xFF10B981);
    const redAccent = Color(0xFFEF4444);
    const textMuted = Color(0xFF8A9BAE);

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
          "Bookmarks",
          style: TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
      ),
      body: SafeArea(
        child: bookmarks.isEmpty
            ? Center(
                child: Padding(
                  padding: const EdgeInsets.all(24.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Icon(
                        Icons.star,
                        color: textMuted.withOpacity(0.4),
                        size: 72,
                      ),
                      const SizedBox(height: 16),
                      const Text(
                        "No Saved Bookmarks",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                      const SizedBox(height: 6),
                      const Text(
                        "Toggle the bookmark button inside quiz sessions to save key problems for review here.",
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
              )
            : ListView.builder(
                padding: const EdgeInsets.all(16),
                itemCount: bookmarks.length,
                itemBuilder: (context, i) {
                  final bookmark = bookmarks[i];
                  final isExpanded = _expandMap[bookmark.questionText] ?? false;

                  return Container(
                    margin: const EdgeInsets.only(bottom: 12.0),
                    decoration: BoxDecoration(
                      color: slateSurface,
                      border: Border.all(color: slateBorder),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: InkWell(
                      onTap: () {
                        setState(() {
                          _expandMap[bookmark.questionText] = !isExpanded;
                        });
                      },
                      borderRadius: BorderRadius.circular(12),
                      child: Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            // Metadata Subject and delete button
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Container(
                                  decoration: BoxDecoration(
                                    color: blueAccent.withOpacity(0.12),
                                    borderRadius: BorderRadius.circular(4),
                                  ),
                                  padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                                  child: Text(
                                    bookmark.subject.toUpperCase(),
                                    style: const TextStyle(
                                      fontSize: 10,
                                      fontWeight: FontWeight.bold,
                                      color: blueAccent,
                                      floating: null,
                                    ),
                                  ),
                                ),
                                IconButton(
                                  key: Key("delete_bookmark_${bookmark.subject.toLowerCase()}_$i"),
                                  icon: const Icon(Icons.delete, color: redAccent, size: 18),
                                  constraints: const BoxConstraints(),
                                  padding: EdgeInsets.zero,
                                  onPressed: () {
                                    provider.deleteBookmarkDirectly(bookmark.questionText);
                                  },
                                ),
                              ],
                            ),
                            const SizedBox(height: 12),

                            // Question Text
                            Text(
                              bookmark.questionText,
                              style: const TextStyle(
                                fontSize: 14.5,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                                height: 1.4,
                              ),
                            ),

                            // Expanded guidelines
                            if (isExpanded) ...[
                              const SizedBox(height: 14),
                              const Divider(color: slateBorder),
                              const SizedBox(height: 10),

                              const Text(
                                "Correct Answer",
                                style: TextStyle(
                                  fontSize: 11,
                                  fontWeight: FontWeight.bold,
                                  color: emeraldAccent,
                                ),
                              ),
                              const SizedBox(height: 3),
                              Text(
                                bookmark.optionsList[bookmark.correctIndex],
                                style: const TextStyle(fontSize: 13.5, color: Colors.white),
                              ),
                              const SizedBox(height: 14),

                              const Text(
                                "Step Solution",
                                style: TextStyle(
                                  fontSize: 11,
                                  fontWeight: FontWeight.bold,
                                  color: blueAccent,
                                ),
                              ),
                              const SizedBox(height: 3),
                              Text(
                                bookmark.explanation,
                                style: const TextStyle(
                                  fontSize: 13,
                                  color: textMuted,
                                  height: 1.4,
                                ),
                              ),
                            ],
                          ],
                        ),
                      ),
                    ),
                  );
                },
              ),
      ),
    );
  }
}
