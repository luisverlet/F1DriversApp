// AddScreen.kt
package com.example.f1driversapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.f1driversapp.components.drivers.DriverForm
import com.example.f1driversapp.ui.theme.F1DriversAppTheme

@Composable
fun AddScreen(
    navigateBack: () -> Unit = {}
) {
    F1DriversAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                DriverForm(
                    onDriverCreated = {
                        navigateBack()
                    },
                    context = context,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}