package com.example.testappbankcard

import android.app.Application
import com.example.testappbankcard.data.database.CardDatabase

class CardApplication : Application() {
    
    companion object {
        lateinit var instance: CardApplication
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        CardDatabase.getDatabase(this)
    }
}

