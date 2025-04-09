package com.example.f1driversapp.data

import androidx.annotation.DrawableRes
import com.example.f1driversapp.R

data class Driver(
    @DrawableRes val imageResId: Int,
    val name: String,
    val score : String,
    val about: String,
    val squad: String,

)

val drivers = listOf(
    Driver(R.drawable.lecrecnd, "Charles Leclerc", "5.0", "The fastest driver in the world", "Ferrari"),
    Driver(R.drawable.hamiltonnd, "Lewis Hamilton", "1.6", "The slowest driver in the world", "Mercedes"),
)