package com.example.pneumoniadetectionjetpackcompose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerNav(
    navController: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    ModalDrawerSheet {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.Blue)
        )
        NavigationDrawerItem(
            label = { Text("About") },
            selected = currentRoute == "About",
            onClick = {
                coroutineScope.launch { drawerState.close() }
                navController.navigate("About") {
                    popUpTo(0)
                }
            },
            icon = { Icon(Icons.Default.Info, contentDescription = "About") }
        )
        NavigationDrawerItem(
            label = { Text("Guide") },
            selected = currentRoute == "Guide",
            onClick = {
                coroutineScope.launch { drawerState.close() }
                navController.navigate("Guide") {
                    popUpTo(0)
                }
            },
            icon = { Icon(Icons.Default.Lock, contentDescription = "Guide") }
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = currentRoute == "Settings",
            onClick = {
                coroutineScope.launch { drawerState.close() }
                navController.navigate("Settings") {
                    popUpTo(0)
                }
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )
    }
}
