package com.example.pneumoniadetectionjetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pneumoniadetectionjetpackcompose.navigation.BottomNav
import com.example.pneumoniadetectionjetpackcompose.navigation.DrawerNav
import com.example.pneumoniadetectionjetpackcompose.ui.theme.PneumoniaDetectionJetpackComposeTheme
import com.example.pneumoniadetectionjetpackcompose.uiux.AboutPage
import com.example.pneumoniadetectionjetpackcompose.uiux.GuidePage
import com.example.pneumoniadetectionjetpackcompose.uiux.HistoryPage
import com.example.pneumoniadetectionjetpackcompose.uiux.HomePage
import com.example.pneumoniadetectionjetpackcompose.uiux.InfoPage
import com.example.pneumoniadetectionjetpackcompose.uiux.ScanPage
import com.example.pneumoniadetectionjetpackcompose.uiux.SettingsPage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var isDarkTheme by remember { mutableStateOf(false) }
            PneumoniaDetectionJetpackComposeTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerNav(
                            navController = navController,
                            coroutineScope = coroutineScope,
                            drawerState = drawerState
                        )
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Pneumonia Detection App") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        coroutineScope.launch {
                                            if (drawerState.isOpen) {
                                                drawerState.close()
                                            } else {
                                                drawerState.open()
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        },
                        bottomBar = { BottomNav(navController) }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "Home",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("Home") { HomePage(navController) }
                            composable("Scan") { ScanPage(navController) }
                            composable("History") { HistoryPage(navController) }
                            composable("Info") { InfoPage(navController) }
                            composable("About") { AboutPage(navController) }
                            composable("Guide") { GuidePage(navController) }
                            composable("Settings") {
                                SettingsPage(
                                    navController,
                                    isDarkTheme = isDarkTheme,
                                    onToggleTheme = { isDarkTheme = !isDarkTheme })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PneumoniaDetectionJetpackComposeTheme {
        Greeting("Android")
    }
}
