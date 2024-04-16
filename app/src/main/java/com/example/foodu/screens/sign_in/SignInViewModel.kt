package com.example.foodu.screens.sign_in

import androidx.navigation.NavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.model.service.AccountService
import com.example.foodu.screens.FoodUAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
) : FoodUAppViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
//    val isBlank = MutableStateFlow(true)

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(navController: NavController) {
        launchCatching {
            accountService.signIn(email.value, password.value)
            navController.navigate(route = HOME_SCREEN)
        }
    }

    fun onSignUpClick(navController: NavController) {
        navController.navigate(route = HOME_SCREEN)
    }
}