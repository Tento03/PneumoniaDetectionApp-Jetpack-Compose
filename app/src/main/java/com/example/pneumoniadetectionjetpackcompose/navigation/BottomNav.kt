package com.example.pneumoniadetectionjetpackcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pneumoniadetectionjetpackcompose.model.Bottom

@Composable
fun BottomNav(navController: NavController,modifier: Modifier = Modifier) {
    val item= listOf(
        Bottom("Home",Icons.Default.Home,"Home"),
        Bottom("Scan",Icons.Default.Search,"Scan"),
        Bottom("History",Icons.Default.DateRange,"History"),
        Bottom("Info",Icons.Default.Info,"Info"),
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentPage=currentBackStackEntry?.destination?.route

    NavigationBar {
        item.forEachIndexed { index, bottom ->
            NavigationBarItem(
                selected = currentPage==bottom.route,
                onClick = {
                    navController.navigate(bottom.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState=true
                        }
                        launchSingleTop=true
                        restoreState=true
                    }
                },
                icon = { Icon(bottom.icon,null) },
                label = { Text(bottom.title) },
            )
        }
    }
}