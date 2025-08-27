package com.example.testappbankcard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testappbankcard.ui.components.CardInfoCard
import com.example.testappbankcard.ui.components.ErrorView
import com.example.testappbankcard.ui.components.LoadingIndicator
import com.example.testappbankcard.ui.state.CardUiState

enum class Screen {
    SEARCH, HISTORY
}

@Composable
fun CardScreen(viewModel: CardViewModel) {
    var currentScreen by remember { mutableStateOf(Screen.SEARCH) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (currentScreen) {
                Screen.SEARCH -> SearchContent(viewModel = viewModel)
                Screen.HISTORY -> HistoryScreen(viewModel = viewModel)
            }
        }
        
        BottomNavigation(
            currentScreen = currentScreen,
            onScreenChange = { currentScreen = it }
        )
    }
}

@Composable
private fun SearchContent(viewModel: CardViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var cardNumber by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
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

@Composable
private fun BottomNavigation(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit
) {
    androidx.compose.material3.Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF5F5F5),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onScreenChange(Screen.SEARCH) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentScreen == Screen.SEARCH) {
                            Color(0xFF2196F3)
                        } else {
                            Color(0xFFE0E0E0)
                        }
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Text(
                        text = "🔍 Поиск",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentScreen == Screen.SEARCH) Color.White else Color.Black
                    )
                }
                
                Button(
                    onClick = { onScreenChange(Screen.HISTORY) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentScreen == Screen.HISTORY) {
                            Color(0xFF2196F3)
                        } else {
                            Color(0xFFE0E0E0)
                        }
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Text(
                        text = "📋 История",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentScreen == Screen.HISTORY) Color.White else Color.Black
                    )
                }
            }

            Text(
                text = "Текущий экран: ${if (currentScreen == Screen.SEARCH) "Поиск" else "История"}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
