package com.example.foodu.model.service.impl

import com.example.foodu.model.service.AccountService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject



class AccountServiceImpl @Inject constructor() : AccountService {

    private var _errorMessage: String = ""
    override suspend fun signIn(email: String, password: String) {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            // Manejar el error y establecer el mensaje de error adecuado
            _errorMessage = when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Correo electrónico inválido."
                "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta."
                "ERROR_USER_NOT_FOUND" -> "Usuario no encontrado."
                // Otros códigos de error según la documentación de Firebase Auth
                else -> "Error de autenticación: ${e.errorCode}"
            }
            throw e
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }
}