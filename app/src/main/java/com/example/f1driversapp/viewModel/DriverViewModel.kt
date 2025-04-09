package com.example.f1driversapp.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1driversapp.models.Driver
import com.example.f1driversapp.repository.ApiFunctions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class DriverViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Para almacenar imágenes en cache
    private val _driverImages = mutableMapOf<Int, ImageBitmap>()
    val driverImages: Map<Int, ImageBitmap> get() = _driverImages

    init {
        loadDrivers()
    }

    private fun loadDrivers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val driversList = ApiFunctions.fetchAllDrivers()
                _drivers.value = driversList

                // Precarga las imágenes de los pilotos
                driversList.forEach { driver ->
                    loadDriverImage(driver.id)
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar los pilotos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadDriverImage(id: Int) {
        try {
            val imageResponse = ApiFunctions.fetchDriverImage(id)
            val bitmap = convertResponseBodyToBitmap(imageResponse)
            bitmap?.let {
                _driverImages[id] = it.asImageBitmap()
            }
        } catch (e: Exception) {
            println("Error cargando imagen del piloto $id: ${e.message}")
        }
    }

    private fun convertResponseBodyToBitmap(responseBody: ResponseBody): Bitmap? {
        return try {
            responseBody.byteStream().use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            println("Error al convertir ResponseBody a Bitmap: ${e.message}")
            null
        }
    }

    fun getDriverImage(id: Int): ImageBitmap? {
        return _driverImages[id]
    }

    fun refreshData() {
        loadDrivers()
    }
}