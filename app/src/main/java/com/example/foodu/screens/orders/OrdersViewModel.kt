package com.example.foodu.screens.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.foodu.HOME_SCREEN
import com.example.foodu.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    init{
        Log.d(ERROR_TAG, "ViewModel Loaded!")
    }
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

    private fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch (
            CoroutineExceptionHandler() { _, throwable ->
                Log.d(ERROR_TAG, throwable.message.orEmpty())
            },
            block = block
        )
    companion object {
        const val ERROR_TAG = "FOOD APP ERROR"
    }
}