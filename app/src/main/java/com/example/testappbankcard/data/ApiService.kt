package com.example.testappbankcard.data

import com.example.testappbankcard.model.Card
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{number}")
    suspend fun loadCardData(
        @Path("number") number: String
    ): Response<Card>

}

