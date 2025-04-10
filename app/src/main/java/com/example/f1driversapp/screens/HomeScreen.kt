package com.example.f1driversapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1driversapp.R
import com.example.f1driversapp.components.drivers.DriverTopAppBar
import com.example.f1driversapp.components.drivers.DriversCard
import com.example.f1driversapp.components.drivers.F1SearchBar
import com.example.f1driversapp.components.loading.LoadingComponent
import com.example.f1driversapp.models.Driver
import com.example.f1driversapp.ui.theme.F1DriversAppTheme
import com.example.f1driversapp.viewModel.DriverViewModel

@Composable
fun HomeScreen() {
    F1DriversAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            DriverApp()
        }
    }
}

@Composable
fun DriverApp() {
    val driverViewModel: DriverViewModel = viewModel()
    val drivers by driverViewModel.drivers.collectAsState()
    val isLoading by driverViewModel.isLoading.collectAsState()
    val error by driverViewModel.error.collectAsState()
    val driverImages = driverViewModel.driverImages
    val isDeletingDriver by driverViewModel.isDeletingDriver.collectAsState()

    val context = LocalContext.current

    var searchText by remember { mutableStateOf("") }
    var driverToDelete by remember { mutableStateOf<Driver?>(null) }

    if (driverToDelete != null) {
        AlertDialog(
            onDismissRequest = { driverToDelete = null },
            title = { Text(stringResource(R.string.delete_confirmation_title)) },
            text = { Text(text = stringResource(R.string.delete_confirmation_message), color = MaterialTheme.colorScheme.onSecondaryContainer) },
            confirmButton = {
                Button(
                    onClick = {
                        driverToDelete?.id?.let { driverId ->
                            driverViewModel.deleteDriver(
                                driverId = driverId,
                                onSuccess = {
                                    Toast.makeText(context, "Driver Deleted", Toast.LENGTH_SHORT).show()
                                    driverToDelete = null
                                },
                                onError = { errorMsg ->
                                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                                    driverToDelete = null
                                }
                            )
                        }
                    }
                ) {
                    Text((stringResource(R.string.confirm_delete)))
                }
            },
            dismissButton = {
                Button(onClick = { driverToDelete = null }) {
                    Text((stringResource(R.string.cancel)))
                }
            }
        )
    }

    val filteredDrivers = if (searchText.isBlank()) {
        drivers
    } else {
        drivers.filter { driver ->
            driver.nombre?.contains(searchText, ignoreCase = true) == true ||
                    driver.escuderia?.contains(searchText, ignoreCase = true) == true
        }
    }

    Scaffold(
        topBar = {
            Column {
                DriverTopAppBar()
                F1SearchBar(
                    searchText = searchText,
                    onSearchTextChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    LoadingComponent(
                        message = stringResource(R.string.loading_pilots),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null && drivers.isEmpty() -> {
                    Text(
                        text = error ?: stringResource(R.string.unknown_error),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        contentPadding = padding,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredDrivers) { driver ->
                            DriversCard(
                                driver = driver,
                                driverImage = driverImages[driver.id],
                                isLoading = false,
                                onDelete = { driverId ->
                                    // Buscar el piloto a eliminar
                                    driverToDelete = drivers.find { it.id == driverId }
                                },
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                            )
                        }
                    }
                }
            }
        }
    }
}