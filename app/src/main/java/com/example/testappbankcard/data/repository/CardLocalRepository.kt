package com.example.testappbankcard.data.repository

import com.example.testappbankcard.data.dao.CardDao
import com.example.testappbankcard.data.entity.CardEntity
import com.example.testappbankcard.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardLocalRepository(private val cardDao: CardDao) {
    
    fun getAllCards(): Flow<List<Card>> {
        return cardDao.getAllCards().map { entities ->
            entities.map { it.toCard() }
        }
    }
    
    suspend fun saveCard(card: Card, cardNumber: String) {
        val entity = CardEntity.fromCard(card, cardNumber)
        cardDao.insertCard(entity)
    }
    
    suspend fun clearAllCards() {
        cardDao.deleteAllCards()
    }
}

