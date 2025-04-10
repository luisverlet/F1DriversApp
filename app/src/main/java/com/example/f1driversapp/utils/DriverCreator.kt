package com.example.f1driversapp.utils

import android.content.Context
import android.net.Uri
import com.example.f1driversapp.models.Driver
import com.example.f1driversapp.repository.ApiFunctions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DriverCreator {
    fun createDriver(
        name: String,
        description: String,
        escuderia: String,
        rating: Int,
        imageUri: Uri?,
        context: Context,
        coroutineScope: CoroutineScope,
        onStartLoading: () -> Unit,
        onFinishLoading: () -> Unit,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        coroutineScope.launch {
            try {
                onStartLoading()

                val driver = Driver(
                    nombre = name,
                    descripcion = description,
                    escuderia = escuderia,
                    rating = rating
                )

                val response = withContext(Dispatchers.IO) {
                    ApiFunctions.createDriver(driver)
                }

                if (response.isSuccessful && response.body() != null) {
                    val createdDriver = response.body()!!
                    val driverId = createdDriver.id

                    if (imageUri != null && driverId != null) {
                        val imageResponse = withContext(Dispatchers.IO) {
                            ApiFunctions.uploadDriverImage(driverId, imageUri, context)
                        }

                        if (!imageResponse.isSuccessful) {
                            onError("Driver created but failed to upload image")
                            onFinishLoading()
                            return@launch
                        }
                    }

                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Failed to create driver: ${response.errorBody()?.string() ?: "Unknown error"}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Error: ${e.message ?: "Unknown error"}")
                }
            } finally {
                onFinishLoading()
            }
        }
    }
}