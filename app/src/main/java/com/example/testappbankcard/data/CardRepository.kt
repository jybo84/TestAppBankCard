package com.example.testappbankcard.data

import com.example.testappbankcard.model.Card
import retrofit2.Response

class CardRepository {

    suspend fun loadCardInfoFromNet(number: String): Response<Card> {
        return ApiFactory.retrofit.loadCardData(number)
    }
}