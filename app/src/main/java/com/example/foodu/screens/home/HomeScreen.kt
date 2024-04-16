package com.example.foodu.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home Icon") },
                label = { Text(text = "Home") },
                selected = true,
                onClick = { /*TODO*/ }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Promotions Icon") },
                label = { Text(text = "Promotions") },
                selected = false,
                onClick = { /*TODO*/ }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Star, contentDescription = "Restaurants Icon") },
                label = { Text(text = "Restaurants") },
                selected = false,
                onClick = { /*TODO*/ }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Order Icon") },
                label = { Text(text = "Orders") },
                selected = false,
                onClick = { /*TODO*/ }
            )
        }
    }
}