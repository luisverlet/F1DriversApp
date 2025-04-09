package com.example.f1driversapp.repository

import com.example.f1driversapp.api.ApiClient
import com.example.f1driversapp.models.Driver
import okhttp3.ResponseBody

object ApiFunctions {
    private val api = ApiClient.apiService

    suspend fun fetchAllDrivers(): List<Driver> {
        return api.getDrivers()
    }

    suspend fun fetchDriverById(id: Int): Driver {
        return api.getDriverById(id)
    }

    suspend fun fetchDriverImage(id: Int): ResponseBody {
        return api.getImageDriver(id)
    }
}
