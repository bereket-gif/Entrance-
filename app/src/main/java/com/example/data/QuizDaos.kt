package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE questionText = :questionText")
    suspend fun deleteBookmark(questionText: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE questionText = :questionText LIMIT 1)")
    suspend fun isBookmarked(questionText: String): Boolean
}

@Dao
interface QuizHistoryDao {
    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<QuizHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: QuizHistoryEntity)

    @Query("DELETE FROM quiz_history")
    suspend fun clearHistory()
}
