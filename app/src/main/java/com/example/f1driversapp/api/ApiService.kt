package com.example.f1driversapp.api


import android.media.Image
import com.example.f1driversapp.models.Driver
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pilotos")
    suspend fun getDrivers(): List<Driver>

    @GET("drivers/{id}")
    suspend fun getDriverById(@Path("id") id: Int): Driver

    @GET("pilotos/{id}/imagen")
    suspend fun getImageDriver(@Path("id") id: Int): ResponseBody // Usando okhttp3.ResponseBody
}
