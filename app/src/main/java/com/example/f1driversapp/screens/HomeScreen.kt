package com.example.f1driversapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1driversapp.R
import com.example.f1driversapp.components.DriverTopAppBar
import com.example.f1driversapp.components.DriversScreen
import com.example.f1driversapp.components.F1SearchBar
import com.example.f1driversapp.components.LoadingComponent
import com.example.f1driversapp.ui.theme.F1DriversAppTheme
import com.example.f1driversapp.viewModel.DriverViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.viewmodel.compose.viewModel

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

    var searchText by remember { mutableStateOf("") }

    // Filtrar pilotos según la búsqueda
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
                isLoading && drivers.isEmpty() -> {
                    // Mostrar pantalla completa de carga solo cuando no hay datos
                    LoadingComponent(
                        message = "Cargando pilotos...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null && drivers.isEmpty() -> {
                    // Mostrar error cuando no se pueden cargar los pilotos
                    Text(
                        text = error ?: "Error desconocido",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                else -> {
                    // Mostrar la lista de pilotos
                    LazyColumn(
                        contentPadding = padding,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredDrivers) { driver ->
                            DriversScreen(
                                driver = driver,
                                driverImage = driverImages[driver.id],
                                isLoading = isLoading && driverImages[driver.id] == null,
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}