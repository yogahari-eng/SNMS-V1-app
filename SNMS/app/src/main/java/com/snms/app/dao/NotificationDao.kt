package com.snms.app.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.snms.app.database.NotificationEntity
import com.snms.app.database.Priority

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): LiveData<List<NotificationEntity>>

    @Query("SELECT * FROM notifications WHERE priority = :priority ORDER BY timestamp DESC")
    fun getNotificationsByPriority(priority: Priority): LiveData<List<NotificationEntity>>

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestNotification(): NotificationEntity?

    @Query("SELECT COUNT(*) FROM notifications")
    fun getTotalCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM notifications WHERE priority = :priority")
    fun getCountByPriority(priority: Priority): LiveData<Int>

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotification(id: Long)

    @Query("DELETE FROM notifications")
    suspend fun deleteAll()

    @Query("""
        SELECT * FROM notifications 
        WHERE title = :title AND content = :content 
        AND timestamp > :since 
        LIMIT 1
    """)
    suspend fun findDuplicate(title: String, content: String, since: Long): NotificationEntity?
}
