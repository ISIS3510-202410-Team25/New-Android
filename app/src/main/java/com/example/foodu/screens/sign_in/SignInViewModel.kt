package com.example.foodu.screens.sign_in

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.model.service.AccountService
import com.example.foodu.screens.FoodUAppViewModel
import com.example.foodu.screens.sign_up.isValidEmail
import com.example.foodu.screens.sign_up.isValidPassword
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
) : FoodUAppViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    // Check validation
    var invalidEmail = MutableStateFlow(false)
    var invalidPassword = MutableStateFlow(false)

    private val auth: FirebaseAuth = Firebase.auth
    fun signInWithGoogleCredential(credential: AuthCredential, navController: NavController) = viewModelScope.launch {
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("LOGIN", "Login with Google successful")
                        navController.navigate(route = HOME_SCREEN)
                    }
                }
                .addOnFailureListener{
                    Log.d("LOGIN", "Login Failed!")
                }
        } catch (ex: Exception) {
            Log.d("LOGIN", "Something went wrong!")
        }
    }

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(navController: NavController) {
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

            accountService.signIn(email.value, password.value)
            navController.navigate(route = HOME_SCREEN)
        }
    }

    fun onSignUpClick(navController: NavController) {
        navController.navigate(route = HOME_SCREEN)
    }
}