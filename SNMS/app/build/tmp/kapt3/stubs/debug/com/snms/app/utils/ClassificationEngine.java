package com.snms.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/snms/app/utils/ClassificationEngine;", "", "()V", "HIGH_KEYWORDS", "", "", "HIGH_PRIORITY_PACKAGES", "", "LOW_KEYWORDS", "MEDIUM_PRIORITY_PACKAGES", "classify", "Lcom/snms/app/database/Priority;", "packageName", "title", "content", "app_debug"})
public final class ClassificationEngine {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> HIGH_PRIORITY_PACKAGES = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> HIGH_KEYWORDS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> MEDIUM_PRIORITY_PACKAGES = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> LOW_KEYWORDS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.snms.app.utils.ClassificationEngine INSTANCE = null;
    
    private ClassificationEngine() {
        super();
    }
    
    /**
     * Classify a notification into HIGH / MEDIUM / LOW priority.
     * Processing target: < 100ms
     */
    @org.jetbrains.annotations.NotNull()
    public final com.snms.app.database.Priority classify(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String content) {
        return null;
    }
}