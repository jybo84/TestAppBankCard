package com.example.testappbankcard.model


class Card(
    val id: Int = 0,
    val scheme: String = "",
    val type: String? = null,
    val brand: String? = null,
    val country: CountryInfo? = null,
    val bank: BankInfo? = null
) {

    data class CountryInfo(
        val name: String? = null,
        val currency: String? = null,
    )

    data class BankInfo(
        val name: String? = null,
        val url: String? = null,
        val phone: String? = null,
        val city: String? = null
    )
}