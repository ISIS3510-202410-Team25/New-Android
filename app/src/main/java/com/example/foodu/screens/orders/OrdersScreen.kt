package com.example.foodu.screens.orders



import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.ORDERS_SCREEN
import com.example.foodu.PROMOTIONS_SCREEN
import com.example.foodu.R
import com.example.foodu.RESTAURANTS_SCREEN
import com.example.foodu.components.TopBar

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    vm: OrdersViewModel = hiltViewModel()
) {
    val currOrders by vm.currentOrders.collectAsState(initial = emptyList())
    val histOrders by vm.historicOrders.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        vm.createData()
        vm.fetchAllOrders()

    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Top bar
        TopBar(text = "Orders")

        // The content
        Text(text = "Current orders: " +currOrders.size,style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black)
        Text(text = "Check the state of your orders: ")
        Text(text = "    ")
        if (currOrders.isEmpty()) {
            Text(text = "You haven't made any order yet.")
        } else {
        LazyColumn {
            items(currOrders) { item ->
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
                        Text(text = item.restaurantAddress ?: "No Address")
                        Text(text = "${item.total} $ COP")
                    }
                }
            }
        }
        }

        Text(text = "History of orders" +currOrders.size,style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black)
        Text(text = "Check your latest orders and restaurants: ")
        Text(text = "   ")
        if (histOrders.isEmpty()) {
            Text(text = "You haven't made any order yet.")
        } else {
            LazyColumn(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
            ) {
                items(histOrders) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            ) {
                                Text(text = item.restaurantName ?: "Res not found")
                                Text(text = item.restaurantAddress ?: "No Address")
                                Text(text = "${item.total} $ COP")
                            }
                            // Aquí puedes agregar la imagen si es necesario
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
                            icon = {
                                Icon(
                                    Icons.Filled.Percent,
                                    contentDescription = "Promotions Icon"
                                )
                            },
                            label = { Text(text = "Promotions") },
                            selected = false,
                            onClick = { navController.navigate(route = PROMOTIONS_SCREEN) }
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Filled.Store,
                                    contentDescription = "Restaurants Icon"
                                )
                            },
                            label = { Text(text = "Restaurants") },
                            selected = false,
                            onClick = { navController.navigate(route = RESTAURANTS_SCREEN) }
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Filled.NoteAlt,
                                    contentDescription = "Order Icon"
                                )
                            },
                            label = { Text(text = "Orders") },
                            selected = true,
                            onClick = { navController.navigate(route = ORDERS_SCREEN) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color(
                                    0xFF83C5BE
                                )
                            )
                        )
                    }
                }
            }
        }
}
