import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.compose.rememberNavController


@Preview(showBackground = true)
@Composable
fun PreviewCouponsScreen() {
    val navController = rememberNavController()
    CouponsScreen(navController = navController)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    text: String,
    navController: NavController = rememberNavController(),
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF83C5BE),
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = text,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}
@Composable
fun CouponsScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {

        TopBar(text = "Buscar", navController = navController)

        // Dynamic Boxes
        repeat(5) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Coupon $index")
            }
        }

        // Barra de navegación al fondo
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