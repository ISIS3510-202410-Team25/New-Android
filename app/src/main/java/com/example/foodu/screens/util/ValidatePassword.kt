package com.example.foodu.screens.util

import android.util.Patterns

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if(password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 6 characters"
            )
        }

        val containsLetterAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }

        if(!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}