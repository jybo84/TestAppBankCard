package com.example.testappbankcard.di

import android.content.Context
import androidx.room.Room
import com.example.testappbankcard.data.CardRepository
import com.example.testappbankcard.data.database.CardDatabase
import com.example.testappbankcard.data.repository.CardLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCardDatabase(
        @ApplicationContext context: Context
    ): CardDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CardDatabase::class.java,
            "card_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideCardDao(database: CardDatabase) = database.cardDao()

    @Provides
    @Singleton
    fun provideCardLocalRepository(cardDao: com.example.testappbankcard.data.dao.CardDao): CardLocalRepository {
        return CardLocalRepository(cardDao)
    }

    @Provides
    @Singleton
    fun provideCardRepository(localRepository: CardLocalRepository): CardRepository {
        return CardRepository(localRepository)
    }
}

