package com.example.testappbankcard.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappbankcard.data.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {
    
    private val repository = CardRepository()
    
    private val _uiState = MutableStateFlow<CardUiState>(CardUiState.Initial)
    val uiState: StateFlow<CardUiState> = _uiState.asStateFlow()
    
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
    
    fun resetState() {
        _uiState.value = CardUiState.Initial
    }
}
