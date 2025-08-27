package com.example.testappbankcard.ui.state

import com.example.testappbankcard.model.Card

sealed class CardUiState {
    object Initial : CardUiState()
    object Loading : CardUiState()
    data class Success(val card: Card) : CardUiState()
    data class Error(val message: String) : CardUiState()
}
