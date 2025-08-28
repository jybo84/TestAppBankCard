package com.example.testappbankcard.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testappbankcard.model.Card

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardNumber: String,
    val scheme: String,
    val type: String?,
    val brand: String?,
    val bankName: String?,
    val countryName: String?,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toCard(): Card {
        return Card(
            id = id.toInt(),
            scheme = scheme,
            type = type,
            brand = brand,
            country = Card.CountryInfo(
                name = countryName,
                currency = null
            ),
            bank = Card.BankInfo(
                name = bankName,
                url = null,
                phone = null,
                city = null
            )
        )
    }

    companion object {
        fun fromCard(card: Card, cardNumber: String): CardEntity {
            return CardEntity(
                cardNumber = cardNumber,
                scheme = card.scheme,
                type = card.type,
                brand = card.brand,
                bankName = card.bank?.name,
                countryName = card.country?.name
            )
        }
    }
}