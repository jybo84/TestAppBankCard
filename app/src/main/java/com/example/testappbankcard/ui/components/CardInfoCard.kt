package com.example.testappbankcard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testappbankcard.model.Card

@Composable
fun CardInfoCard(card: Card) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Информация о карте",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Схема", card.scheme.ifEmpty { "Не указано" })
            InfoRow("Тип", card.type ?: "Не указано")
            InfoRow("Бренд", card.brand ?: "Не указано")
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Страна",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            card.country?.let { country ->
                InfoRow("Название", country.name ?: "Не указано")
                InfoRow("Валюта", country.currency ?: "Не указано")
            } ?: run {
                InfoRow("Название", "Не указано")
                InfoRow("Валюта", "Не указано")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Банк",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            card.bank?.let { bank ->
                InfoRow("Название", bank.name ?: "Не указано")
                InfoRow("Город", bank.city ?: "Не указано")
                InfoRow("Телефон", bank.phone ?: "Не указано")
                InfoRow("Сайт", bank.url ?: "Не указано")
            } ?: run {
                InfoRow("Название", "Не указано")
                InfoRow("Город", "Не указано")
                InfoRow("Телефон", "Не указано")
                InfoRow("Сайт", "Не указано")
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

