class Question {
  final String subject;
  final String question;
  final List<String> options;
  final int correct;
  final String explanation;

  Question({
    required this.subject,
    required this.question,
    required this.options,
    required this.correct,
    required this.explanation,
  });
}
