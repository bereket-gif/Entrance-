class Question {
  final String subject;
  final String question;
  final String optionA;
  final String optionB;
  final String optionC;
  final String optionD;
  final int correctOption;
  final String explanation;

  Question({
    required this.subject,
    required this.question,
    required this.optionA,
    required this.optionB,
    required this.optionC,
    required this.optionD,
    required this.correctOption,
    required this.explanation,
  });

  Map<String, dynamic> toJson() {
    return {
      'subject': subject,
      'question': question,
      'optionA': optionA,
      'optionB': optionB,
      'optionC': optionC,
      'optionD': optionD,
      'correctOption': correctOption,
      'explanation': explanation,
    };
  }

  factory Question.fromJson(Map<String, dynamic> json) {
    return Question(
      subject: json['subject'] as String,
      question: json['question'] as String,
      optionA: json['optionA'] as String,
      optionB: json['optionB'] as String,
      optionC: json['optionC'] as String,
      optionD: json['optionD'] as String,
      correctOption: json['correctOption'] as int,
      explanation: json['explanation'] as String,
    );
  }
}
