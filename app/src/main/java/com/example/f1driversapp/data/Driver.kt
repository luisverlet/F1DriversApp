package com.example.f1driversapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.f1driversapp.R

data class Driver(
    @DrawableRes val imageResId: Int,
    @StringRes val name: Int,
    val score : String,
    @StringRes val about: Int,
    @StringRes val squad: Int,

)

val drivers = listOf(
    Driver(R.drawable.lecrecnd, R.string.driver1, "2", R.string.driver1_about, R.string.driver1_squad),
    Driver(R.drawable.hamiltonnd, R.string.driver2, "16", R.string.driver2_about, R.string.driver2_squad),
)