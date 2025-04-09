package com.example.f1driversapp.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.f1driversapp.R
import com.example.f1driversapp.data.Driver

@Composable
fun DriversScreen(
    driver: Driver,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = androidx.compose.ui.Alignment.Center

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
                DriverIcon(driver.imageResId)
                DriverInfo(driver.name, driver.score)
            }
        }
    }
}

@Composable
fun DriverInfo(name: Int, squad: Int) {

    Column(modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))) {
        Text(
            text = stringResource(name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))

        )
        Text(
            text = stringResource(squad),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )
    }

}

@Composable
fun DriverIcon(
    @DrawableRes driverIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(driverIcon),
        contentDescription = null
    )
}