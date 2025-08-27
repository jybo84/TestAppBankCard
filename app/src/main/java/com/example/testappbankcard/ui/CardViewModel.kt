package com.example.testappbankcard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappbankcard.data.CardRepository
import com.example.testappbankcard.model.Card
import com.example.testappbankcard.ui.state.CardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {
    
    private val repository = CardRepository()
    
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

                        addToHistory(card)
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
    
    private fun addToHistory(card: Card) {
        val currentHistory = _searchHistory.value.toMutableList()

        currentHistory.removeAll { it.id == card.id }

        currentHistory.add(0, card)

        if (currentHistory.size > 10) {
            currentHistory.removeAt(currentHistory.size - 1)
        }
        _searchHistory.value = currentHistory
    }
    
    fun clearHistory() {
        _searchHistory.value = emptyList()
    }
    
    fun resetState() {
        _uiState.value = CardUiState.Initial
    }
}
