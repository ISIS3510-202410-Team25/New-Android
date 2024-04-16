package com.example.foodu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodu.screens.home.HomeScreen
import com.example.foodu.screens.sign_in.SignInScreen
import com.example.foodu.screens.sign_up.SignUpScreen
import com.example.foodu.ui.theme.FoodUTheme

@Composable
fun FoodUApp() {
    FoodUTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            FoodUNavigation()
        }
    }
}

@Composable
fun FoodUNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SIGN_IN_SCREEN
    ) {

        
        composable(SIGN_IN_SCREEN) {
            SignInScreen(modifier = Modifier, navController = navController)
        }
        
        composable(SIGN_UP_SCREEN) {
            SignUpScreen(modifier = Modifier, navController = navController)
        }
        
        composable(HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
    }
}
