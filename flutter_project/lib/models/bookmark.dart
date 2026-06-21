import 'question.dart';

class Bookmark {
  final String questionText;
  final String subject;
  final String optionA;
  final String optionB;
  final String optionC;
  final String optionD;
  final int correctOption;
  final String explanation;
  final int timestamp;

  Bookmark({
    required this.questionText,
    required this.subject,
    required this.optionA,
    required this.optionB,
    required this.optionC,
    required this.optionD,
    required this.correctOption,
    required this.explanation,
    required this.timestamp,
  });

  Question toQuestion() {
    return Question(
      subject: subject,
      question: questionText,
      optionA: optionA,
      optionB: optionB,
      optionC: optionC,
      optionD: optionD,
      correctOption: correctOption,
      explanation: explanation,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'questionText': questionText,
      'subject': subject,
      'optionA': optionA,
      'optionB': optionB,
      'optionC': optionC,
      'optionD': optionD,
      'correctOption': correctOption,
      'explanation': explanation,
      'timestamp': timestamp,
    };
  }

  factory Bookmark.fromJson(Map<String, dynamic> json) {
    return Bookmark(
      questionText: json['questionText'] as String,
      subject: json['subject'] as String,
      optionA: json['optionA'] as String,
      optionB: json['optionB'] as String,
      optionC: json['optionC'] as String,
      optionD: json['optionD'] as String,
      correctOption: json['correctOption'] as int,
      explanation: json['explanation'] as String,
      timestamp: json['timestamp'] as int,
    );
  }
}
