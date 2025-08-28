package com.example.testappbankcard.data

import com.example.testappbankcard.data.repository.CardLocalRepository
import com.example.testappbankcard.model.Card
import retrofit2.Response

class CardRepository(
    private val localRepository: CardLocalRepository
) {

    suspend fun loadCardInfoFromNet(number: String): Response<Card> {
        return ApiFactory.retrofit.loadCardData(number)
    }

    suspend fun saveCardToLocal(card: Card, cardNumber: String) {
        localRepository.saveCard(card, cardNumber)
    }

    fun getLocalCards() = localRepository.getAllCards()

    suspend fun clearLocalCards() {
        localRepository.clearAllCards()
    }
}