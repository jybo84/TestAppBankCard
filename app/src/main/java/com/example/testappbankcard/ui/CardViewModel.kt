package com.example.testappbankcard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappbankcard.CardApplication
import com.example.testappbankcard.data.CardRepository
import com.example.testappbankcard.data.database.CardDatabase
import com.example.testappbankcard.data.repository.CardLocalRepository
import com.example.testappbankcard.model.Card
import com.example.testappbankcard.ui.state.CardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {

    // Используем Application из CardApplication
    private val database = CardDatabase.getDatabase(CardApplication.instance)
    private val localRepository = CardLocalRepository(database.cardDao())
    private val repository = CardRepository(localRepository)

    private val _uiState = MutableStateFlow<CardUiState>(CardUiState.Initial)
    val uiState: StateFlow<CardUiState> = _uiState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<Card>>(emptyList())
    val searchHistory: StateFlow<List<Card>> = _searchHistory.asStateFlow()

    fun loadCardInfo(cardNumber: String) {
        if (cardNumber.isBlank()) {
            _uiState.value = CardUiState.Error("Введите номер карты")
            return
        }

        if (cardNumber.length < 6) {
            _uiState.value = CardUiState.Error("Номер карты должен содержать минимум 6 цифр")
            return
        }

        viewModelScope.launch {
            _uiState.value = CardUiState.Loading

            try {
                val response = repository.loadCardInfoFromNet(cardNumber)

                if (response.isSuccessful) {
                    response.body()?.let { card ->
                        _uiState.value = CardUiState.Success(card)

                        repository.saveCardToLocal(card, cardNumber)

                        loadHistoryFromLocal()
                    } ?: run {
                        _uiState.value = CardUiState.Error("Не удалось получить данные карты")
                    }
                } else {
                    _uiState.value = CardUiState.Error("Ошибка сервера: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = CardUiState.Error("Ошибка сети: ${e.message}")
            }
        }
    }

    private fun loadHistoryFromLocal() {
        viewModelScope.launch {
            repository.getLocalCards().collect { cards ->
                _searchHistory.value = cards
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearLocalCards()
            _searchHistory.value = emptyList()
        }
    }

    fun resetState() {
        _uiState.value = CardUiState.Initial
    }

    init {
        loadHistoryFromLocal()
    }
}

