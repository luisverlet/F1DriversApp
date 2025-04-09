package com.example.f1driversapp.navigation

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home

import com.example.f1driversapp.models.NavItem

object NavItemList {
    val NavItemList = listOf(
        NavItem(label = "Home", icon = Icons.Default.Home),
        NavItem(label = "Add", icon = Icons.Default.AddCircle)
    )
}