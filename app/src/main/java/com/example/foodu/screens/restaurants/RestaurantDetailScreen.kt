package com.example.foodu.screens.restaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.ORDERS_SCREEN
import com.example.foodu.PROMOTIONS_SCREEN
import com.example.foodu.R
import com.example.foodu.RESTAURANTS_DETAIL_SCREEN
import com.example.foodu.RESTAURANTS_SCREEN
import com.example.foodu.components.TopBar
@Preview(showBackground = true)
@Composable
fun RestaurantDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
){
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top Bar
        TopBar(text = "Restaurants")
        Text(
            text = "El corral",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.salad),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    
                    text = "Hamburguesas El Corral offers a delectable array of burgers, each crafted with the finest ingredients and bursting with flavor. From classic beef patties cooked to perfection to innovative combinations featuring premium toppings and sauces, every bite promises a savory delight",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )}
        }

        LazyColumn {
            items(items = listOf("Corral", "Callejera", "Corralita")) {
                Card(modifier = modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .padding(8.dp)) {
                    Row {
                        Column(modifier = Modifier
                            .width(250.dp)
                            .padding(8.dp)) {
                            Text(text = it, modifier = modifier.padding(start = 16.dp, top = 16.dp), style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Plate description and specifications", modifier = modifier.padding(top = 16.dp, start = 16.dp), style = MaterialTheme.typography.bodyLarge)
                            Button(
                                onClick = {  },
                                modifier = modifier
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                    .width(120.dp)
                                    .height(40.dp),

                                ) {
                                Text("Add to cart")
                            }
                        }
                        Column {
                            Image(painter = painterResource(R.drawable.salad), contentDescription = null)
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
