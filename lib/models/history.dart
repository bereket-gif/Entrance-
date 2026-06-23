class QuizHistory {
  final int? id;
  final String subject;
  final int score;
  final int totalCount;
  final int timestamp;

  QuizHistory({
    this.id,
    required this.subject,
    required this.score,
    required this.totalCount,
    required this.timestamp,
  });

  // Convert to Map for insert
  Map<String, dynamic> toMap() {
    final map = {
      'subject': subject,
      'score': score,
      'totalCount': totalCount,
      'timestamp': timestamp,
    };
    if (id != null) {
      map['id'] = id as num;
    }
    return map;
  }

  // Convert from database map
  factory QuizHistory.fromMap(Map<String, dynamic> map) {
    return QuizHistory(
      id: map['id'] as int?,
      subject: map['subject'] as String,
      score: map['score'] as int,
      totalCount: map['totalCount'] as int,
      timestamp: map['timestamp'] as int,
    );
  }
}
