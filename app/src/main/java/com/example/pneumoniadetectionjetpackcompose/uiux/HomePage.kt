package com.example.pneumoniadetectionjetpackcompose.uiux

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pneumoniadetectionjetpackcompose.history.HistoryViewModel
import com.example.pneumoniadetectionjetpackcompose.model.History
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun HomePage(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var prediction by remember { mutableStateOf<String?>(null) }
    var lastDate by remember { mutableStateOf<String?>(null) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { selectedUri ->
        selectedUri?.let {
            uri = it
            val bmp = if (Build.VERSION.SDK_INT < 28) {
                Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
            bitmap = bmp
            prediction = PneumoniaPredictor.predict(context, bmp)
            lastDate = getTodayDate()
            val uid = UUID.randomUUID().toString()
            val history = History(uid, prediction.toString(), "", it.toString(), lastDate)
            viewModel.addHistory(history)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { selectedBitmap ->
        selectedBitmap?.let {
            bitmap = it
            prediction = PneumoniaPredictor.predict(context, it)
            lastDate = getTodayDate()
            val uid = UUID.randomUUID().toString()
            val history = History(uid, prediction.toString(), "", bitmap.toString(), lastDate)
            viewModel.addHistory(history)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "📱 Pneumonia Detector",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "📝 Aplikasi untuk membantu deteksi dini pneumonia melalui foto X-ray paru-paru.",
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { imageLauncher.launch("image/*") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Rounded.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Dari Galeri")
            }

            Button(
                onClick = { cameraLauncher.launch(null) },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Rounded.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Kamera")
            }
        }
        Spacer(modifier.height(30.dp))
        bitmap?.let {
            Image(
                it.asImageBitmap(),
                null,
                modifier = Modifier
                    .clip(shape = RectangleShape)
                    .height(300.dp)
                    .width(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Kartu status deteksi
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "📈 Deteksi dilakukan: ${if (prediction != null) "1 kali" else "0 kali"}",
                    fontWeight = FontWeight.Medium
                )
                Text("🧾 Hasil terakhir: ${prediction ?: "-"} ${lastDate?.let { "pada $it" } ?: ""}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = {
            navController.navigate("About") {
                launchSingleTop = true
                restoreState = true
                popUpTo("Home") {
                    inclusive = false
                }
            }
        }) {
            Icon(Icons.Rounded.Info, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Apa itu pneumonia? Gejala? → Klik di sini")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    navController.navigate("Guide") {
                        popUpTo("Home") {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Panduan")
            }
            OutlinedButton(
                onClick = {
                    navController.navigate("Info") {
                        popUpTo("Home") {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Info")
            }
            OutlinedButton(
                onClick = {
                    navController.navigate("History") {
                        popUpTo("Home") {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Riwayat")
            }
        }
    }
}

fun getTodayDate(): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    return sdf.format(Date())
}
