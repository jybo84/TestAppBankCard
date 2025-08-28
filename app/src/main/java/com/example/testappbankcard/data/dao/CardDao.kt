package com.example.testappbankcard.data.dao

import androidx.room.*
import com.example.testappbankcard.data.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    
    @Query("SELECT * FROM cards ORDER BY timestamp DESC")
    fun getAllCards(): Flow<List<CardEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity): Long
    
    @Query("DELETE FROM cards")
    suspend fun deleteAllCards()
}

