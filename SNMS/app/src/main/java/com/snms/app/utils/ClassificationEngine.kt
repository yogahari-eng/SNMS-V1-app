package com.snms.app.utils

import com.snms.app.database.Priority

object ClassificationEngine {

    // HIGH priority package names
    private val HIGH_PRIORITY_PACKAGES = setOf(
        "com.android.phone",
        "com.google.android.dialer",
        "com.samsung.android.incallui",
        "com.miui.incallui",
        "com.android.server.telecom",
        // Banking apps
        "com.sbi.lotusintouch",
        "com.csam.icici.bank.imobile",
        "com.hdfcbank.app",
        "com.axis.mobile",
        "net.one97.paytm",
        "com.phonepe.app",
        "in.org.npci.upiapp",
        "com.google.android.apps.nbu.paisa.user",
        "com.mobikwik_new",
        "com.amazon.mShop.android.shopping"
    )

    // HIGH priority keywords (case-insensitive)
    private val HIGH_KEYWORDS = listOf(
        "otp", "one time password", "urgent", "alert", "emergency",
        "transaction", "debit", "credit", "payment", "bank",
        "fraud", "security", "verification", "code", "pin",
        "missed call", "incoming call", "low battery", "virus",
        "warning", "critical", "important", "action required"
    )

    // MEDIUM priority package names
    private val MEDIUM_PRIORITY_PACKAGES = setOf(
        "com.whatsapp",
        "com.whatsapp.w4b",
        "com.google.android.gm",
        "com.microsoft.android.email",
        "com.android.mms",
        "com.google.android.apps.messaging",
        "com.samsung.android.messaging",
        "org.telegram.messenger",
        "com.facebook.messenger",
        "com.instagram.android",
        "com.twitter.android",
        "com.linkedin.android",
        "com.snapchat.android",
        "com.discord"
    )

    // LOW priority keywords
    private val LOW_KEYWORDS = listOf(
        "offer", "sale", "discount", "promo", "promotion",
        "deal", "buy", "shop", "limited time", "exclusive",
        "subscribe", "newsletter", "ad", "advertisement",
        "reward", "cashback", "win", "won", "congratulations"
    )

    /**
     * Classify a notification into HIGH / MEDIUM / LOW priority.
     * Processing target: < 100ms
     */
    fun classify(packageName: String, title: String, content: String): Priority {
        val combinedText = "$title $content".lowercase()

        // 1. Check HIGH priority packages (calls, banking)
        if (packageName in HIGH_PRIORITY_PACKAGES) return Priority.HIGH

        // 2. Check HIGH keywords
        if (HIGH_KEYWORDS.any { combinedText.contains(it) }) return Priority.HIGH

        // 3. Check LOW keywords first (promos/ads override medium packages)
        if (LOW_KEYWORDS.any { combinedText.contains(it) }) return Priority.LOW

        // 4. Check MEDIUM packages
        if (packageName in MEDIUM_PRIORITY_PACKAGES) return Priority.MEDIUM

        // 5. Default LOW
        return Priority.LOW
    }
}
