package com.snms.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Priority { HIGH, MEDIUM, LOW }

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val appName: String,
    val packageName: String,
    val title: String,
    val content: String,
    val priority: Priority,
    val timestamp: Long,
    val isRead: Boolean = false
)
