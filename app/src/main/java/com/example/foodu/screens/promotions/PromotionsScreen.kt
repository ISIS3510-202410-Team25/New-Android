package com.example.foodu.screens.promotions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.ORDERS_SCREEN
import com.example.foodu.PROMOTIONS_SCREEN
import com.example.foodu.RESTAURANTS_SCREEN
import com.example.foodu.components.TopBar

@Preview(showBackground = true)
@Composable
fun PromotionsScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController() ,
    vm: PromotionsViewModel = hiltViewModel()
) {
    val currOffers by vm.currentPromotions.collectAsState(initial = emptyList())
    val histOrders by vm.historicPromotions.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        vm.createData()
        vm.fetchAllPromotions()
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top bar
        TopBar(text = "Promotions")

        Text(text = "Current offers: " +currOffers.size,style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black)
        Text(text = "This are our promos for today: ")
        Text(text = "    ")
        if (currOffers.isEmpty()) {
            Text(text = "No offers yet!")
        } else {
            LazyColumn {
                items(currOffers) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Añadir un espacio entre las tarjetas
                        shape = RoundedCornerShape(8.dp), // Ajustar la forma de la tarjeta
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = item.restaurantName ?: "Res not found")
                            Text(text = "30 % de descuento!")
                        }
                    }
                }
            }
        }
    }
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
                    selected = true,
                    onClick = { navController.navigate(route = PROMOTIONS_SCREEN) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF83C5BE))
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Store, contentDescription = "Restaurants Icon") },
                    label = { Text(text = "Restaurants") },
                    selected = false,
                    onClick = { navController.navigate(route = RESTAURANTS_SCREEN) }
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