package com.example.foodu.screens.restaurants


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.ORDERS_SCREEN
import com.example.foodu.PROMOTIONS_SCREEN
import com.example.foodu.R
import com.example.foodu.RESTAURANTS_DETAIL_SCREEN
import com.example.foodu.RESTAURANTS_SCREEN
import com.example.foodu.components.TopBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer

// @Preview(showBackground = true)
@Composable
fun RestaurantsScreen(

    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),

    ) {
    //val isConnected = viewModel.isConnected.value ?: false

    //if (!isConnected) {
    //    Text("No hay conexión a Internet")
    //}

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top Bar
        TopBar(text = "Restaurants")

        TextField(
            value = "Search",
            onValueChange = { /*TODO*/ },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        //        .border(1.dp, Color.Black, CircleShape),
            placeholder = { Text(text = "Search...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
        )

        LazyColumn {
            items(items = listOf("El corral", "Subway", "Randy's")) {
                Card(modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp)) {
                    Row {
                        Column(modifier = Modifier
                            .width(250.dp)
                            .padding(8.dp)) {
                            Text(text = it, modifier = modifier.padding(start = 16.dp, top = 16.dp), style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Fast food place", modifier = modifier.padding(top = 16.dp, start = 16.dp), style = MaterialTheme.typography.bodyLarge)
                            Button(
                                onClick = { navController.navigate(route = RESTAURANTS_DETAIL_SCREEN) },
                                modifier = modifier
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                    .width(120.dp)
                                    .height(40.dp),

                            ) {
                                Text("Menu")
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