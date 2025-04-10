package com.example.f1driversapp.components.drivers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.f1driversapp.R
import okhttp3.ResponseBody

@Composable
fun DriverIcon(
    imageResponse: ResponseBody?,
    isLoading: Boolean,
    driverName: String?,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        LoadingDriverIcon()
    } else if (imageResponse != null) {
        DriverImageFromResponse(imageResponse, driverName, modifier)
    } else {
        DefaultDriverImage(modifier, driverName)
    }
}

@Composable
private fun LoadingDriverIcon() {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_smaller)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
private fun DriverImageFromResponse(
    imageResponse: ResponseBody,
    driverName: String?,
    modifier: Modifier
) {
    val imageBytes = remember(imageResponse) {
        try {
            imageResponse.bytes()
        } catch (e: Exception) {
            null
        }
    }

    if (imageBytes != null) {
        AsyncImage(
            model = imageBytes,
            contentDescription = "Imagen de $driverName",
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .padding(dimensionResource(R.dimen.padding_smaller))
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop
        )
    } else {
        DefaultDriverImage(modifier, driverName)
    }
}

@Composable
fun DefaultDriverImage(modifier: Modifier, driverName: String?) {
    Image(
        painter = painterResource(id = R.drawable.avatar_svgrepo_com),
        contentDescription = "Imagen predeterminada para $driverName",
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_smaller))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}