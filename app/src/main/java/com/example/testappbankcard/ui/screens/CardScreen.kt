package com.example.testappbankcard.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardScreen(viewModel: CardViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var cardNumber by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Поиск информации о карте",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Номер карты (минимум 6 цифр)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { viewModel.loadCardInfo(cardNumber) },
            modifier = Modifier.fillMaxWidth(),
            enabled = cardNumber.isNotBlank() && uiState !is CardUiState.Loading
        ) {
            Text("Найти информацию")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        when (uiState) {
            is CardUiState.Initial -> {
                Text(
                    text = "Введите номер карты для получения информации",
                    fontSize = 16.sp
                )
            }
            
            is CardUiState.Loading -> {
                LoadingIndicator()
            }
            
            is CardUiState.Success -> {
                CardInfoCard(card = (uiState as CardUiState.Success).card)
            }
            
            is CardUiState.Error -> {
                ErrorView(
                    message = (uiState as CardUiState.Error).message,
                    onRetry = { viewModel.loadCardInfo(cardNumber) }
                )
            }
        }
    }
}
