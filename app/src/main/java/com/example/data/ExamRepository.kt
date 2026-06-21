package com.example.data

import kotlinx.coroutines.flow.Flow

class ExamRepository(
    private val bookmarkDao: BookmarkDao,
    private val quizHistoryDao: QuizHistoryDao
) {
    val allBookmarks: Flow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()
    val allHistory: Flow<List<QuizHistoryEntity>> = quizHistoryDao.getAllHistory()

    suspend fun insertBookmark(bookmark: BookmarkEntity) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun deleteBookmark(questionText: String) {
        bookmarkDao.deleteBookmark(questionText)
    }

    suspend fun isBookmarked(questionText: String): Boolean {
        return bookmarkDao.isBookmarked(questionText)
    }

    suspend fun insertHistory(history: QuizHistoryEntity) {
        quizHistoryDao.insertHistory(history)
    }

    suspend fun clearHistory() {
        quizHistoryDao.clearHistory()
    }
}
