package com.example.f1driversapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
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

    private val _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    private val _isDeletingDriver = MutableStateFlow(false)
    val isDeletingDriver: StateFlow<Boolean> = _isDeletingDriver.asStateFlow()

    val driverImages = mutableStateMapOf<Int?, ResponseBody?>()

    private var imagesLeftToLoad = 0

    init {
        fetchDrivers()
    }

    fun fetchDrivers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            driverImages.clear()
            try {
                val driversList = ApiFunctions.fetchAllDrivers()
                _drivers.value = driversList

                imagesLeftToLoad = driversList.count { it.id != null }

                if (imagesLeftToLoad == 0) {
                    _isLoading.value = false
                    return@launch
                }

                driversList.forEach { driver ->
                    driver.id?.let { driverId ->
                        loadDriverImage(driverId)
                    }
                }
            } catch (e: Exception) {
                Log.e("DriverViewModel", "Error fetching drivers", e)
                _error.value = "Failed to load drivers: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    private fun loadDriverImage(id: Int) {
        viewModelScope.launch {
            try {
                val image = ApiFunctions.fetchDriverImage(id)
                driverImages[id] = image
            } catch (e: Exception) {
                Log.e("DriverViewModel", "Error loading image for driver $id", e)
                driverImages[id] = null
            } finally {
                imagesLeftToLoad--
                if (imagesLeftToLoad <= 0) {
                    _isLoading.value = false
                }
            }
        }
    }

    fun deleteDriver(driverId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isDeletingDriver.value = true

                val response = ApiFunctions.deleteDriver(driverId)

                if (response.isSuccessful) {

                    _drivers.value = _drivers.value.filter { it.id != driverId }

                    driverImages.remove(driverId)
                    onSuccess()
                } else {
                    val errorMsg = "Error al eliminar piloto: ${response.errorBody()?.string() ?: "Error desconocido"}"
                    Log.e("DriverViewModel", errorMsg)
                    onError(errorMsg)
                }
            } catch (e: Exception) {
                val errorMsg = "Error al eliminar piloto: ${e.message ?: "Error desconocido"}"
                Log.e("DriverViewModel", errorMsg, e)
                onError(errorMsg)
            } finally {
                _isDeletingDriver.value = false
            }
        }
    }
}