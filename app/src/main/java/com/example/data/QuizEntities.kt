package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val questionText: String,
    val subject: String,
    val optionsString: String, // Composed of options separated by ";;"
    val correctIndex: Int,
    val explanation: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun getOptionsList(): List<String> {
        return optionsString.split(";;")
    }
}

@Entity(tableName = "quiz_history")
data class QuizHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val score: Int,
    val totalCount: Int,
    val timestamp: Long = System.currentTimeMillis()
)
