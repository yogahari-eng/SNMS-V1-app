package com.snms.app;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\f"}, d2 = {"Lcom/snms/app/SNMSApplication;", "Landroid/app/Application;", "()V", "database", "Lcom/snms/app/database/SNMSDatabase;", "getDatabase", "()Lcom/snms/app/database/SNMSDatabase;", "database$delegate", "Lkotlin/Lazy;", "onCreate", "", "Companion", "app_debug"})
public final class SNMSApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy database$delegate = null;
    private static com.snms.app.SNMSApplication instance;
    @org.jetbrains.annotations.NotNull()
    public static final com.snms.app.SNMSApplication.Companion Companion = null;
    
    public SNMSApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.snms.app.database.SNMSDatabase getDatabase() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/snms/app/SNMSApplication$Companion;", "", "()V", "<set-?>", "Lcom/snms/app/SNMSApplication;", "instance", "getInstance", "()Lcom/snms/app/SNMSApplication;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.snms.app.SNMSApplication getInstance() {
            return null;
        }
    }
}