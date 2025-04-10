package com.example.f1driversapp.utils

import android.content.Context
import com.example.f1driversapp.R

object DriverValidator {
    fun validateFields(
        context: Context,
        name: String,
        escuderia: String,
        rating: String,
        onNameError: (String) -> Unit,
        onEscuderiaError: (String) -> Unit,
        onRatingError: (String) -> Unit
    ): Boolean {
        var isValid = true

        if (name.isBlank()) {
            onNameError(context.getString(R.string.error_empty_name))
            isValid = false
        }

        if (escuderia.isBlank()) {
            onEscuderiaError(context.getString(R.string.error_empty_squad))
            isValid = false
        }

        if (rating.isBlank()) {
            onRatingError(context.getString(R.string.error_empty_rating))
            isValid = false
        } else {
            try {
                val ratingValue = rating.toInt()
                if (ratingValue < 0 || ratingValue > 100) {
                    onRatingError(context.getString(R.string.error_rating_range))
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                onRatingError(context.getString(R.string.error_invalid_rating))
                isValid = false
            }
        }

        return isValid
    }
}