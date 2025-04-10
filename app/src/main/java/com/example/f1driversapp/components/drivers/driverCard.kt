package com.example.f1driversapp.components.drivers

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.example.f1driversapp.R
import com.example.f1driversapp.components.buttons.DeleteButton
import com.example.f1driversapp.components.buttons.DriverItemButton
import com.example.f1driversapp.models.Driver
import com.example.f1driversapp.viewModel.DriverViewModel
import okhttp3.ResponseBody

@Composable
fun DriversCard(
    driver: Driver,
    driverImage: ResponseBody?,
    isLoading: Boolean = false,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        DriverCard(
            driver = driver,
            driverImage = driverImage,
            isLoading = isLoading,
            onDelete = onDelete,
            modifier = modifier.fillMaxWidth(0.95f)
        )
    }
}

@Composable
private fun DriverCard(
    driver: Driver,
    driverImage: ResponseBody?,
    isLoading: Boolean,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            // Cabecera de la tarjeta
            DriverCardHeader(
                driver = driver,
                driverImage = driverImage,
                isLoading = isLoading,
                expanded = expanded,
                onExpandClick = { expanded = !expanded }
            )

            // Contenido expandible
            if (expanded) {
                DriverCardExpandedContent(
                    driver = driver,
                    onDeleteClick = {
                        driver.id?.let { onDelete(it) }
                    }
                )
            }
        }
    }
}

@Composable
private fun DriverCardHeader(
    driver: Driver,
    driverImage: ResponseBody?,
    isLoading: Boolean,
    expanded: Boolean,
    onExpandClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DriverIcon(driverImage, isLoading, driver.nombre)
        DriverInfo(driver.nombre ?: "Nombre no disponible", driver.escuderia ?: "Escudería no disponible")
        Spacer(Modifier.weight(1f))
        DriverItemButton(
            onClick = onExpandClick,
            expanded = expanded
        )
    }
}

@Composable
private fun DriverCardExpandedContent(
    driver: Driver,
    onDeleteClick: () -> Unit,
    isDeleting: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.padding_small))
    ) {
        DriverAbout(
            driverAbout = driver.descripcion ?: "Información no disponible",
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small)
                )
        )
        Column(
            modifier = Modifier
                .padding(end = dimensionResource(R.dimen.padding_small))
                .align(Alignment.CenterVertically)
        ) {
            DriverScore(
                driver.rating?.toString() ?: "0",
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )
            DeleteButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.End),
                isLoading = isDeleting
            )
        }
    }
}