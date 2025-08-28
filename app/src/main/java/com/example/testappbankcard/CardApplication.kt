package com.example.testappbankcard

import android.app.Application
import com.example.testappbankcard.data.database.CardDatabase

class CardApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()

        CardDatabase.getDatabase(this)
    }
}

