package com.example.foodu.screens.orders

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodu.R
import com.example.foodu.util.rememberImeState

@Composable
fun OrdersScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val orderList = ""
    val pastOrderList = ""

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }
    @Composable
    fun TopPanel(onHomeClick: () -> Unit) {
        NavigationBar {
            NavigationBarItem(
                //painter = painterResource(R.drawable.logo),
                //contentDescription = "Bowl Salad Banner",
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home Icon") },
                label = { Text(text = "FOODU") },
                selected = true,
                onClick = onHomeClick
            )
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopPanel(onHomeClick = {

        })
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