package com.example.f1driversapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
// Emulador:   private const val BASE_URL = "http://10.0.2.2:8080/api/"
private const val BASE_URL = "https://f1-backend-production-8e57.up.railway.app/api/"


    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}