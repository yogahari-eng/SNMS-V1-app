package com.snms.app.utils

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import com.snms.app.services.SNMSNotificationListenerService

object PermissionUtils {

    /**
     * Returns true if the Notification Listener permission is granted.
     */
    fun isNotificationListenerEnabled(context: Context): Boolean {
        val pkgName = context.packageName
        val flat = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        ) ?: return false
        if (flat.isBlank()) return false
        val names = flat.split(":")
        val expected = ComponentName(pkgName, SNMSNotificationListenerService::class.java.name).flattenToString()
        return names.any { it.trim() == expected }
    }
}
