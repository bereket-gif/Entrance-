import 'dart:async';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import '../models/bookmark.dart';
import '../models/history.dart';

class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._init();
  static Database? _database;

  DatabaseHelper._init();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('ethio_matric.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = await getDatabasesPath();
    final path = join(dbPath, filePath);

    return await openDatabase(
      path,
      version: 1,
      onCreate: _createDB,
    );
  }

  Future<void> _createDB(Database db, int version) async {
    await db.execute('''
      CREATE TABLE bookmarks (
        questionText TEXT PRIMARY KEY,
        subject TEXT NOT NULL,
        optionsString TEXT NOT NULL,
        correctIndex INTEGER NOT NULL,
        explanation TEXT NOT NULL,
        timestamp INTEGER NOT NULL
      )
    ''');

    await db.execute('''
      CREATE TABLE quiz_history (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        subject TEXT NOT NULL,
        score INTEGER NOT NULL,
        totalCount INTEGER NOT NULL,
        timestamp INTEGER NOT NULL
      )
    ''');
  }

  // --- BOOKMARKS SOLUTIONS ---

  Future<int> insertBookmark(Bookmark bookmark) async {
    final db = await instance.database;
    return await db.insert(
      'bookmarks',
      bookmark.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<List<Bookmark>> getBookmarks() async {
    final db = await instance.database;
    final orderBy = 'timestamp DESC';
    final result = await db.query('bookmarks', orderBy: orderBy);
    return result.map((json) => Bookmark.fromMap(json)).toList();
  }

  Future<bool> isBookmarked(String questionText) async {
    final db = await instance.database;
    final maps = await db.query(
      'bookmarks',
      columns: ['questionText'],
      where: 'questionText = ?',
      whereArgs: [questionText],
    );
    return maps.isNotEmpty;
  }

  Future<int> deleteBookmark(String questionText) async {
    final db = await instance.database;
    return await db.delete(
      'bookmarks',
      where: 'questionText = ?',
      whereArgs: [questionText],
    );
  }

  // --- HISTORY LOG SOLUTIONS ---

  Future<int> insertHistory(QuizHistory history) async {
    final db = await instance.database;
    return await db.insert(
      'quiz_history',
      history.toMap(),
    );
  }

  Future<List<QuizHistory>> getHistory() async {
    final db = await instance.database;
    final orderBy = 'timestamp DESC';
    final result = await db.query('quiz_history', orderBy: orderBy);
    return result.map((json) => QuizHistory.fromMap(json)).toList();
  }

  Future<int> clearHistory() async {
    final db = await instance.database;
    return await db.delete('quiz_history');
  }

  Future<void> close() async {
    final db = await _database;
    if (db != null) {
      await db.close();
    }
  }
}
