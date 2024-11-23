package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jetbrains.kmpapp.screens.booking.BookingScreen
import com.jetbrains.kmpapp.screens.booking.BookingViewModel
import com.jetbrains.kmpapp.screens.list.ListScreen
import com.jetbrains.kmpapp.screens.profile.ProfileScreen
import io.ktor.websocket.Frame


@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigationBar(navController) }
            ) { innerPadding ->
                AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier
    ) {
        composable("list") { ListScreen() }
        composable("booking") {
            val viewModel = remember { BookingViewModel() }
            BookingScreen(viewModel = viewModel)
        }
        composable("profile") { ProfileScreen() }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("list", "booking", "profile")
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        "list" -> Icon(Icons.AutoMirrored.Filled.List, contentDescription = "List")
                        "booking" -> Icon(Icons.Default.DateRange, contentDescription = "Booking")
                        "profile" -> Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                },
                label = { Frame.Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }) },
                selected = currentRoute == screen,
                onClick = { navController.navigate(screen) }
            )
        }
    }
}
