# SNMS – Smart Notification Management System
## Complete Setup & Run Guide

---

## 1. Project Structure

```
SNMS/
├── build.gradle                          ← Project-level Gradle
├── settings.gradle
└── app/
    ├── build.gradle                      ← App-level Gradle (dependencies)
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/snms/app/
        │   ├── SNMSApplication.kt
        │   ├── adapters/
        │   │   └── NotificationAdapter.kt
        │   ├── dao/
        │   │   └── NotificationDao.kt
        │   ├── database/
        │   │   ├── NotificationEntity.kt
        │   │   └── SNMSDatabase.kt
        │   ├── repository/
        │   │   └── NotificationRepository.kt
        │   ├── services/
        │   │   ├── BootReceiver.kt
        │   │   └── SNMSNotificationListenerService.kt
        │   ├── ui/
        │   │   ├── MainActivity.kt
        │   │   ├── NotificationListActivity.kt
        │   │   └── SettingsActivity.kt
        │   ├── utils/
        │   │   ├── ClassificationEngine.kt
        │   │   ├── PermissionUtils.kt
        │   │   └── PrefsUtils.kt
        │   └── viewmodel/
        │       └── NotificationViewModel.kt
        └── res/
            ├── drawable/
            │   ├── badge_background.xml
            │   ├── dot_green.xml
            │   └── dot_red.xml
            ├── layout/
            │   ├── activity_main.xml
            │   ├── activity_notification_list.xml
            │   ├── activity_settings.xml
            │   └── item_notification.xml
            ├── menu/
            │   └── main_menu.xml
            └── values/
                ├── colors.xml
                ├── strings.xml
                └── themes.xml
```

---

## 2. Step-by-Step Setup in Android Studio

### Step 1 — Create a New Project
1. Open **Android Studio** → **New Project**
2. Select **Empty Views Activity**
3. Set:
   - **Name**: SNMS
   - **Package name**: `com.snms.app`
   - **Language**: Kotlin
   - **Minimum SDK**: API 26 (Android 8.0)
4. Click **Finish**

### Step 2 — Replace Gradle Files
Replace the contents of:
- `build.gradle` (project level) → use the provided `build.gradle`
- `app/build.gradle` (app level) → use the provided `app/build.gradle`
- `settings.gradle` → use the provided `settings.gradle`

Then click **Sync Now** in Android Studio.

### Step 3 — Paste All Source Files
Copy each `.kt` file into the matching package folder under `app/src/main/java/com/snms/app/`.

Android Studio will auto-create packages if you right-click the `app` folder → **New → Package**.

### Step 4 — Paste All Resource Files
- `res/layout/` → paste all 4 XML layout files
- `res/drawable/` → paste the 3 drawable XMLs
- `res/values/` → paste `colors.xml`, `strings.xml`, `themes.xml`
- `res/menu/` → create `menu/` folder, paste `main_menu.xml`

### Step 5 — Replace AndroidManifest.xml
Replace the entire `app/src/main/AndroidManifest.xml` with the provided file.

> ⚠️ **Important**: The `<service>` tag for `SNMSNotificationListenerService` MUST include
> `android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"`
> and the correct `<intent-filter>`.

### Step 6 — Build the Project
- Click **Build → Make Project** (or press `Ctrl+F9`)
- Fix any import errors by pressing `Alt+Enter` on red underlines
- Room's annotation processor will auto-generate the database implementation

---

## 3. How to Run the App

1. Connect an Android device (API 26+) or start an emulator
2. Click **Run → Run 'app'** (green play button)
3. The app will install and open to the **Dashboard**

---

## 4. Enabling Notification Access (Critical Step)

The app CANNOT capture notifications without this permission.

### Method A — From within the app
1. Open the app
2. If the yellow banner appears → tap **"Enable"**
3. OR go to **Settings screen** → tap **"Open Notification Access Settings"**

### Method B — Manually on device
1. Go to **Settings → Apps → Special App Access → Notification Access**
   *(exact path varies by manufacturer)*
2. Find **"SNMS"** in the list
3. Toggle it **ON**
4. Tap **Allow** on the confirmation dialog

### Method C — ADB (for emulators)
```bash
adb shell cmd notification allow_listener com.snms.app/com.snms.app.services.SNMSNotificationListenerService
```

After enabling, return to the app. The red dot in the dashboard should turn **green**.

---

## 5. Testing with Real Notifications

### Test 1 — WhatsApp Message (MEDIUM)
1. Send yourself a WhatsApp message from another device/number
2. Check the dashboard — Medium count should increment
3. Tap "View All" to see the notification with a yellow MEDIUM badge

### Test 2 — OTP / Banking (HIGH)
1. Initiate any UPI payment or bank transaction
2. The OTP SMS / notification should appear as HIGH priority
3. Or trigger a missed call — it should appear as HIGH

### Test 3 — Promotional (LOW)
1. Any e-commerce app sale notification, newsletter, or promo
2. Should appear as LOW with green badge

### Test 4 — Focus Mode
1. Go to **Settings** → enable **Focus Mode**
2. Go back to **All Notifications** — only HIGH priority items appear
3. Turn Focus Mode off — all notifications return

### Test 5 — Deduplication
1. Trigger the same notification twice within 10 seconds
2. Only the first one should be saved in the database

---

## 6. Common Errors & Fixes

### ❌ `Cannot find symbol: class NotificationDao_Impl`
**Fix**: Clean and rebuild → **Build → Clean Project** → **Build → Rebuild Project**

Room generates DAO implementations at compile time via `kapt`. Make sure `kapt` is in your `build.gradle` plugins.

---

### ❌ `SNMSNotificationListenerService` not capturing notifications
**Fixes**:
1. Verify permission is enabled (green dot in dashboard)
2. Check Logcat filter: `SNMSListener` — look for log lines
3. Try revoking and re-granting the Notification Access permission
4. On some OEM devices (Xiaomi, OPPO, Vivo): go to Battery settings and disable battery optimisation for SNMS

---

### ❌ `NullPointerException` in notification processing
**Fix**: Already handled — `SNMSNotificationListenerService` safely checks for null extras, title, and content before processing.

---

### ❌ Room database crash: `IllegalStateException: Migration required`
**Fix**: The database uses `fallbackToDestructiveMigration()`. If you change the schema during development, uninstall the app and reinstall.

---

### ❌ App crashes on Android 13+ with notification permission
**Fix**: Android 13 (API 33+) requires `POST_NOTIFICATIONS` permission at runtime for posting. For *reading* notifications via `NotificationListenerService`, only the special access setting is needed (no runtime prompt).

---

### ❌ Toolbar/ActionBar conflict (`supportActionBar` is null)
**Fix**: Make sure `themes.xml` uses `Theme.MaterialComponents.Light.NoActionBar` and the layout includes a `<Toolbar>` with `setSupportActionBar(binding.toolbar)`.

---

### ❌ ViewBinding not working
**Fix**: In `app/build.gradle` under `android { }` make sure:
```groovy
buildFeatures {
    viewBinding true
}
```
Then sync Gradle. Binding classes are auto-generated from layout file names (e.g., `activity_main.xml` → `ActivityMainBinding`).

---

## 7. Architecture Overview

```
UI (Activities) ──observe──▶ ViewModel ──LiveData──▶ Repository
                                                          │
                                                     Room DAO
                                                          │
                                                     SQLite DB
                                                          ▲
                                              NotificationListenerService
                                                  (background, always on)
```

**Data flow:**
1. Android system delivers notification → `SNMSNotificationListenerService.onNotificationPosted()`
2. Service extracts app name, title, content
3. `ClassificationEngine.classify()` returns HIGH / MEDIUM / LOW
4. `NotificationRepository.insertIfNotDuplicate()` checks deduplication then writes to Room
5. LiveData in ViewModel automatically notifies all observing Activities
6. RecyclerView updates instantly with no manual refresh needed

---

## 8. Performance Notes

- Classification is O(n) string match, typically **< 5ms**
- Room writes happen on `Dispatchers.IO` — never on the main thread
- `ListAdapter` with `DiffUtil` ensures only changed items re-render
- No polling: LiveData + Room's built-in `Flow`/`LiveData` support means zero battery waste from timers

---

## 9. Adding More Apps to Classification

Open `ClassificationEngine.kt` and add package names:

```kotlin
// Add to HIGH_PRIORITY_PACKAGES
"com.yourbank.app",

// Add to MEDIUM_PRIORITY_PACKAGES  
"com.yourchat.app",

// Add keywords to HIGH_KEYWORDS
"fraud alert",
```

To find a package name: enable USB debugging, send a notification from the app, then check Logcat with filter `SNMSListener` — the package name is logged for every captured notification.
