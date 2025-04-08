

package com.example.f1driversapp.screens

import com.example.f1driversapp.components.DriverTopAppBar
import com.example.f1driversapp.components.F1SearchBar
import com.example.f1driversapp.components.DriversScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1driversapp.ui.theme.F1DriversAppTheme


@Composable
fun HomeScreen(darkTheme: Boolean = false) {
    F1DriversAppTheme(darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            DriverApp()
        }
    }
}


@Composable
fun DriverApp() {
    var searchText by remember { mutableStateOf("") }

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
    ) { innerPadding ->
        DriversScreen(
            modifier = Modifier.padding(innerPadding),
            searchText = searchText
        )
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Preview(showBackground = true)
@Composable
fun HomeScreenDarkPreview() {
    HomeScreen(darkTheme = true)
}
