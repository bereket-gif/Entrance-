class QuizHistory {
  final String subject;
  final int score;
  final int totalQuestions;
  final int elapsedSeconds;
  final int timestamp;

  QuizHistory({
    required this.subject,
    required this.score,
    required this.totalQuestions,
    required this.elapsedSeconds,
    required this.timestamp,
  });

  Map<String, dynamic> toJson() {
    return {
      'subject': subject,
      'score': score,
      'totalQuestions': totalQuestions,
      'elapsedSeconds': elapsedSeconds,
      'timestamp': timestamp,
    };
  }

  factory QuizHistory.fromJson(Map<String, dynamic> json) {
    return QuizHistory(
      subject: json['subject'] as String,
      score: json['score'] as int,
      totalQuestions: json['totalQuestions'] as int,
      elapsedSeconds: json['elapsedSeconds'] as int,
      timestamp: json['timestamp'] as int,
    );
  }
}
