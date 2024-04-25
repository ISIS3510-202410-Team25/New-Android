package com.example.foodu.screens.restaurants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.ORDERS_SCREEN
import com.example.foodu.PROMOTIONS_SCREEN
import com.example.foodu.RESTAURANTS_SCREEN
import com.example.foodu.components.TopBar

@Preview(showBackground = true)
@Composable
fun RestaurantsScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top Bar
        TopBar(text = "Restaurants")

        /*
        * Here it goes the content
        * */

        // Bottom Bar
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            BottomAppBar {
                NavigationBar(containerColor = Color.White, tonalElevation = 2.dp) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home Icon") },
                        label = { Text(text = "Home") },
                        selected = false,
                        onClick = { navController.navigate(route = HOME_SCREEN) },
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Percent, contentDescription = "Promotions Icon") },
                        label = { Text(text = "Promotions") },
                        selected = false,
                        onClick = { navController.navigate(route = PROMOTIONS_SCREEN) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Store, contentDescription = "Restaurants Icon") },
                        label = { Text(text = "Restaurants") },
                        selected = true,
                        onClick = { navController.navigate(route = RESTAURANTS_SCREEN) },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF83C5BE))
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.NoteAlt, contentDescription = "Order Icon") },
                        label = { Text(text = "Orders") },
                        selected = false,
                        onClick = { navController.navigate(route = ORDERS_SCREEN) },
                    )
                }
            }
        }
    }
}