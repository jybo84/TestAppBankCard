package com.example.testappbankcard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testappbankcard.model.Card

@Composable
fun HistoryScreen(viewModel: CardViewModel) {
    val searchHistory by viewModel.searchHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 35.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "История поиска",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            if (searchHistory.isNotEmpty()) {
                Button(
                    onClick = { viewModel.clearHistory() },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Очистить")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (searchHistory.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "История пуста",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Поищите карты, чтобы увидеть их здесь",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchHistory) { card ->
                    HistoryCardItem(card = card)
                }
            }
        }
    }
}

@Composable
private fun HistoryCardItem(card: Card) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE6F9FA)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = card.scheme.ifEmpty { "Неизвестная система" },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            card.brand?.let { brand ->
                Text(
                    text = "Бренд: $brand",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            card.bank?.name?.let { bankName ->
                Text(
                    text = "Банк: $bankName",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            card.country?.name?.let { countryName ->
                Text(
                    text = "Страна: $countryName",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}