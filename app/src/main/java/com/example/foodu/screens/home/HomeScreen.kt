package com.example.foodu.screens.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodu.components.TopBar
import com.example.foodu.R

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {

    Column(modifier = modifier.fillMaxSize()) {
        TopBar(text = "Home")
        Text(text = "Welcome User!", style = MaterialTheme.typography.titleLarge, modifier = modifier.padding(start = 16.dp, top = 16.dp))

        Text(text = "Satisfy your cravings with just a few taps", style = MaterialTheme.typography.titleSmall, modifier = modifier.padding(start = 16.dp, top = 16.dp))

        TextField(
            value = "Search",
            onValueChange = { /*TODO*/ },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Black, CircleShape),
            placeholder = { Text(text = "Search...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
        )

        Text(text = "Check out our promotions", modifier = modifier.padding(start = 16.dp, top = 8.dp), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        LazyRow {
            items(items = listOf("Title1", "Title2", "Title3")) {
                Card(modifier = modifier
                    .width(250.dp)
                    .height(100.dp)) {
                    Row {
                        Column {
                            Text(text = it, modifier = modifier.padding(start = 16.dp, top = 16.dp), style = MaterialTheme.typography.bodyLarge)
                            Text(text = "This is the description", modifier = modifier.padding(top = 16.dp, start = 16.dp), style = MaterialTheme.typography.bodyLarge)
                            Text(text = "This is the price", modifier = modifier.padding(top = 24.dp, start = 16.dp), style = MaterialTheme.typography.bodySmall)
                        }
                        Column {
                            Image(painter = painterResource(R.drawable.salad), contentDescription = null)
                        }
                    }
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        BottomAppBar {
            NavigationBar(containerColor = Color.White, tonalElevation = 2.dp) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home Icon") },
                    label = { Text(text = "Home") },
                    selected = true,
                    onClick = { /*TODO*/ },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF83C5BE))
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Percent, contentDescription = "Promotions Icon") },
                    label = { Text(text = "Promotions") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Store, contentDescription = "Restaurants Icon") },
                    label = { Text(text = "Restaurants") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.NoteAlt, contentDescription = "Order Icon") },
                    label = { Text(text = "Orders") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}