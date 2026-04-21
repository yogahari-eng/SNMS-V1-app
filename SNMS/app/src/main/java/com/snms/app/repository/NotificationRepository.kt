package com.snms.app.repository

import androidx.lifecycle.LiveData
import com.snms.app.dao.NotificationDao
import com.snms.app.database.NotificationEntity
import com.snms.app.database.Priority

class NotificationRepository(private val dao: NotificationDao) {

    val allNotifications: LiveData<List<NotificationEntity>> = dao.getAllNotifications()
    val totalCount: LiveData<Int> = dao.getTotalCount()
    val highCount: LiveData<Int> = dao.getCountByPriority(Priority.HIGH)
    val mediumCount: LiveData<Int> = dao.getCountByPriority(Priority.MEDIUM)
    val lowCount: LiveData<Int> = dao.getCountByPriority(Priority.LOW)

    fun getByPriority(priority: Priority): LiveData<List<NotificationEntity>> =
        dao.getNotificationsByPriority(priority)

    /**
     * Insert only if not a duplicate within the last 10 seconds.
     */
    suspend fun insertIfNotDuplicate(notification: NotificationEntity): Boolean {
        val tenSecondsAgo = System.currentTimeMillis() - 10_000
        val existing = dao.findDuplicate(
            notification.title,
            notification.content,
            tenSecondsAgo
        )
        return if (existing == null) {
            dao.insertNotification(notification)
            true
        } else {
            false
        }
    }

    suspend fun markAsRead(id: Long) = dao.markAsRead(id)

    suspend fun delete(id: Long) = dao.deleteNotification(id)

    suspend fun clearAll() = dao.deleteAll()
}
