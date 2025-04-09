

package com.example.f1driversapp.screens
import com.example.f1driversapp.components.DriverTopAppBar
import com.example.f1driversapp.components.F1SearchBar
import com.example.f1driversapp.components.DriversScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1driversapp.data.drivers
import com.example.f1driversapp.ui.theme.F1DriversAppTheme
import androidx.compose.foundation.lazy.items
import com.example.f1driversapp.R


@Composable
fun HomeScreen() {
    F1DriversAppTheme() {
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
    ) { it ->
        LazyColumn (contentPadding = it){
            items(drivers) {
                DriversScreen(
                    driver = it,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

