package com.example.f1driversapp.utils

object DriverValidator {
    fun validateFields(
        name: String,
        escuderia: String,
        rating: String,
        onNameError: (String) -> Unit,
        onEscuderiaError: (String) -> Unit,
        onRatingError: (String) -> Unit
    ): Boolean {
        var isValid = true

        if (name.isBlank()) {
            onNameError("Name cannot be empty")
            isValid = false
        }

        if (escuderia.isBlank()) {
            onEscuderiaError("Escuderia cannot be empty")
            isValid = false
        }

        if (rating.isBlank()) {
            onRatingError("Rating cannot be empty")
            isValid = false
        } else {
            try {
                val ratingValue = rating.toInt()
                if (ratingValue < 0 || ratingValue > 100) {
                    onRatingError("Rating must be between 0 and 100")
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                onRatingError("Rating must be a valid number")
                isValid = false
            }
        }

        return isValid
    }
}