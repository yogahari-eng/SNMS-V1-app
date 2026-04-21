package com.snms.app.utils

import android.content.Context
import android.content.SharedPreferences

object PrefsUtils {
    private const val PREFS_NAME = "snms_prefs"
    private const val KEY_FOCUS_MODE = "focus_mode"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getFocusMode(context: Context): Boolean =
        prefs(context).getBoolean(KEY_FOCUS_MODE, false)

    fun setFocusMode(context: Context, enabled: Boolean) =
        prefs(context).edit().putBoolean(KEY_FOCUS_MODE, enabled).apply()
}
