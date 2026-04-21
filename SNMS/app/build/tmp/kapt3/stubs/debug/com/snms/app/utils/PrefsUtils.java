package com.snms.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/snms/app/utils/PrefsUtils;", "", "()V", "KEY_FOCUS_MODE", "", "PREFS_NAME", "getFocusMode", "", "context", "Landroid/content/Context;", "prefs", "Landroid/content/SharedPreferences;", "setFocusMode", "", "enabled", "app_debug"})
public final class PrefsUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "snms_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FOCUS_MODE = "focus_mode";
    @org.jetbrains.annotations.NotNull()
    public static final com.snms.app.utils.PrefsUtils INSTANCE = null;
    
    private PrefsUtils() {
        super();
    }
    
    private final android.content.SharedPreferences prefs(android.content.Context context) {
        return null;
    }
    
    public final boolean getFocusMode(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setFocusMode(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enabled) {
    }
}