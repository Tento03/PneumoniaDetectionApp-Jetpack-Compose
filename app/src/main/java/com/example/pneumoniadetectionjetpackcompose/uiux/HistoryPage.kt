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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pneumoniadetectionjetpackcompose.history.HistoryViewModel
import com.example.pneumoniadetectionjetpackcompose.model.History
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryPage(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val history by viewModel.getHistory.collectAsState()
    viewModel.getHistory()

    Column(verticalArrangement = Arrangement.Bottom) {
        Spacer(modifier.height(30.dp))
        LazyColumn(modifier = modifier.padding(16.dp)) {
            items(history.size) { index ->
                HistoryCard(history = history[index], viewModel)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun HistoryCard(history: History, viewModel: HistoryViewModel, modifier: Modifier = Modifier) {
    // Ubah string date dari history ke objek Date (jika format sesuai)
    val parsedDate = try {
        val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        inputFormat.parse(history.date)
    } catch (e: Exception) {
        null // Kalau gagal parse
    }

    // Lalu format jadi tampilan yang diinginkan
    val dateString = parsedDate?.let {
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        outputFormat.format(it)
    } ?: history.date // Jika parsing gagal, tampilkan string asli

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
                Column(modifier.weight(1f)) {
                    Text(text = history.title, style = MaterialTheme.typography.titleMedium)
                    if (dateString != null) {
                        Text(text = dateString, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Row {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, null)
                    }
                    IconButton(onClick = { viewModel.deleteHistory(history.id) }) {
                        Icon(Icons.Default.Delete, null)
                    }
                }
            }
        }
    }
}

