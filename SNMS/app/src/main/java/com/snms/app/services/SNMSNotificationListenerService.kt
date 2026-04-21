package com.snms.app.services

import android.app.Notification
import android.content.pm.PackageManager
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.snms.app.SNMSApplication
import com.snms.app.database.NotificationEntity
import com.snms.app.repository.NotificationRepository
import com.snms.app.utils.ClassificationEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SNMSNotificationListenerService : NotificationListenerService() {

    private val TAG = "SNMSListener"
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var repository: NotificationRepository

    // Our own package — we never capture ourselves
    private val ownPackage by lazy { applicationContext.packageName }

    override fun onCreate() {
        super.onCreate()
        val db = (applicationContext as SNMSApplication).database
        repository = NotificationRepository(db.notificationDao())
        Log.d(TAG, "NotificationListenerService created")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn ?: return
        if (sbn.packageName == ownPackage) return

        try {
            processNotification(sbn)
        } catch (e: Exception) {
            Log.e(TAG, "Error processing notification: ${e.message}", e)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Optional: could mark notification as dismissed in DB
        Log.d(TAG, "Notification removed: ${sbn?.packageName}")
    }

    private fun processNotification(sbn: StatusBarNotification) {
        val notification = sbn.notification ?: return
        val extras = notification.extras ?: return

        val packageName = sbn.packageName ?: return
        val appName = getAppName(packageName)

        // Extract title safely
        val title: String = extras.getCharSequence(Notification.EXTRA_TITLE)
            ?.toString()
            ?.trim()
            ?.takeIf { it.isNotBlank() }
            ?: extras.getCharSequence(Notification.EXTRA_TITLE_BIG)?.toString()?.trim()
            ?: return  // Skip notifications with no title

        // Extract content safely
        val content: String = extras.getCharSequence(Notification.EXTRA_TEXT)
            ?.toString()
            ?.trim()
            ?: extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()?.trim()
            ?: extras.getCharSequence(Notification.EXTRA_SUMMARY_TEXT)?.toString()?.trim()
            ?: ""

        // Skip system / service notifications with no real content
        if (title.isBlank() && content.isBlank()) return

        val priority = ClassificationEngine.classify(packageName, title, content)

        val entity = NotificationEntity(
            appName = appName,
            packageName = packageName,
            title = title,
            content = content,
            priority = priority,
            timestamp = sbn.postTime
        )

        serviceScope.launch {
            val inserted = repository.insertIfNotDuplicate(entity)
            if (inserted) {
                Log.d(TAG, "Saved [$priority] from $appName: $title")
            } else {
                Log.d(TAG, "Duplicate skipped: $title")
            }
        }
    }

    private fun getAppName(packageName: String): String {
        return try {
            val pm: PackageManager = applicationContext.packageManager
            val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                pm.getApplicationInfo(packageName, 0)
            }
            pm.getApplicationLabel(info).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            packageName.substringAfterLast('.')
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "NotificationListenerService destroyed")
    }
}
