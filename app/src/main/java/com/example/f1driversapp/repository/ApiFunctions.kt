package com.example.f1driversapp.repository

import android.content.Context
import android.net.Uri
import com.example.f1driversapp.api.ApiClient
import com.example.f1driversapp.models.Driver
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

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

    suspend fun createDriver(driver: Driver): Response<Driver> {
        return api.createDriver(driver)
    }

    suspend fun uploadDriverImage(id: Int, imageUri: Uri, context: Context): Response<ResponseBody> {

        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        return api.uploadDriverImage(id, imagePart)
    }

    suspend fun deleteDriver(id: Int): Response<Void> {
        return api.deleteDriver(id)
    }
}