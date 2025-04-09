package com.example.f1driversapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.f1driversapp.R
import com.example.f1driversapp.models.Driver
import okhttp3.ResponseBody

@Composable
fun DriversScreen(
    driver: Driver,
    driverImage: ImageBitmap?,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(0.95f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                // Si está cargando, muestra el indicador, sino la imagen
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.image_size))
                            .padding(dimensionResource(R.dimen.padding_small)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                } else if (driverImage != null) {
                    // Usar la imagen cargada desde el ViewModel
                    Image(
                        bitmap = driverImage,
                        contentDescription = "Imagen de ${driver.nombre}",
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.image_size))
                            .padding(dimensionResource(R.dimen.padding_small))
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Imagen por defecto
                    Image(
                        painter = painterResource(id = R.drawable.avatar_svgrepo_com),
                        contentDescription = "Imagen predeterminada",
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.image_size))
                            .padding(dimensionResource(R.dimen.padding_small))
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop
                    )
                }

                // Información del piloto
                Column(modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))) {
                    Text(
                        text = driver.nombre ?: "Nombre no disponible",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
                    )
                    Text(
                        text = "Score: ${driver.rating}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = driver.escuderia ?: "Escudería no disponible",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}