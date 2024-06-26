package com.example.foodu.screens.sign_up

import androidx.biometric.BiometricPrompt
import androidx.navigation.NavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.model.service.AccountService
import com.example.foodu.screens.FoodUAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
) : FoodUAppViewModel() {

    val username = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    // Check validation
    var invalidEmail = MutableStateFlow(false)
    var invalidPassword = MutableStateFlow(false)

    fun updateUsername(newUsername: String) {
        username.value = newUsername
    }

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignUpClick(navController: NavController) {
        launchCatching {
            if (!email.value.isValidEmail()) {
                invalidEmail.value = true
//                throw IllegalArgumentException("Invalid email format")
            } else {
                invalidEmail.value = !invalidEmail.value
            }

            if (!password.value.isValidPassword()) {
                invalidPassword.value = true
//                throw IllegalArgumentException("Invalid Password")
            } else {
                invalidPassword.value = !invalidPassword.value
            }

            accountService.signUp(email.value, password.value)
            navController.navigate(route = HOME_SCREEN)
        }
    }
}