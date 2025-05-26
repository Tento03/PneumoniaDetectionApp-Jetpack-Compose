package com.example.pneumoniadetectionjetpackcompose.uiux

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pneumoniadetectionjetpackcompose.R
import com.example.pneumoniadetectionjetpackcompose.model.History
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryPage(navController: NavController, modifier: Modifier = Modifier) {
    val history = listOf(
        History(
            "Pneumonia",
            R.drawable.ic_launcher_foreground.toString(),
            Date(125, 4, 23)
        ), // 2025 = 1900 + 125
        History("Normal", R.drawable.ic_launcher_foreground.toString(), Date(125, 4, 23)),
        History("Pneumonia", R.drawable.ic_launcher_foreground.toString(), Date(125, 4, 23))
    )

    Column(verticalArrangement = Arrangement.Bottom) {
        Spacer(modifier.height(30.dp))
        LazyColumn(modifier = modifier.padding(16.dp)) {
            items(history.size) { index ->
                HistoryCard(history = history[index])
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun HistoryCard(history: History, modifier: Modifier = Modifier) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val dateString = dateFormat.format(history.date)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = if (history.image.isNotEmpty()) history.image.toUri() else null,
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = history.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = dateString, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
