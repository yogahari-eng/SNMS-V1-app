package com.snms.app

import android.app.Application
import com.snms.app.database.SNMSDatabase

class SNMSApplication : Application() {

    val database: SNMSDatabase by lazy {
        SNMSDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SNMSApplication
            private set
    }
}
