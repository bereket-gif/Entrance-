class Bookmark {
  final String questionText;
  final String subject;
  final String optionsString; // semicolon-separated options (";;")
  final int correctIndex;
  final String explanation;
  final int timestamp;

  Bookmark({
    required this.questionText,
    required this.subject,
    required this.optionsString,
    required this.correctIndex,
    required this.explanation,
    required this.timestamp,
  });

  // Get options as a clean native list
  List<String> get optionsList => optionsString.split(";;");

  // Convert to map for Sqflite helper
  Map<String, dynamic> toMap() {
    return {
      'questionText': questionText,
      'subject': subject,
      'optionsString': optionsString,
      'correctIndex': correctIndex,
      'explanation': explanation,
      'timestamp': timestamp,
    };
  }

  // Convert from map
  factory Bookmark.fromMap(Map<String, dynamic> map) {
    return Bookmark(
      questionText: map['questionText'] as String,
      subject: map['subject'] as String,
      optionsString: map['optionsString'] as String,
      correctIndex: map['correctIndex'] as int,
      explanation: map['explanation'] as String,
      timestamp: map['timestamp'] as int,
    );
  }
}
