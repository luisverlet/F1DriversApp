package com.example.f1driversapp.models

data class Driver(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String = "",
    val escuderia: String,
    val rating: Int
)