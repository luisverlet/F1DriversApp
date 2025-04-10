package com.example.f1driversapp.api

import com.example.f1driversapp.models.Driver
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("pilotos")
    suspend fun getDrivers(): List<Driver>

    @GET("drivers/{id}")
    suspend fun getDriverById(@Path("id") id: Int): Driver

    @GET("pilotos/{id}/imagen")
    suspend fun getImageDriver(@Path("id") id: Int): ResponseBody

    @POST("pilotos")
    suspend fun createDriver(@Body driver: Driver): Response<Driver>

    @Multipart
    @POST("pilotos/{id}/imagen")
    suspend fun uploadDriverImage(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>

    @DELETE("pilotos/{id}")
    suspend fun deleteDriver(@Path("id") id: Int): Response<Void>

}