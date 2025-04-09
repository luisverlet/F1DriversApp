package com.example.f1driversapp.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
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
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Center
    ) {
        Card(
            modifier = modifier.fillMaxWidth(0.95f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = CenterVertically
                ) {
                    DriverIcon(driver.imageResId)
                    DriverInfo(driver.name, driver.squad)
                    Spacer(Modifier.weight(1f))
                    DriverItemButton(
                        onClick = { expanded = !expanded }
                    )
                }

                if (expanded) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = dimensionResource(R.dimen.padding_small))
                    ) {
                        DriverAbout(
                            driver.about,
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
                                .align(CenterVertically)
                        ) {
                            DriverScore(
                                driver.score,
                                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
                            )
                            DeleteButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.align(End)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DriverInfo(name: String, squad: String) {
    Column(modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small)).background(color = MaterialTheme.colorScheme.primaryContainer)) {
        Text(
            text = name,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = squad,
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
            .padding(dimensionResource(R.dimen.padding_smaller))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(driverIcon),
        contentDescription = null
    )
}

@Composable
fun DriverItemButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = stringResource(R.string.expand_icon_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun DriverAbout(
    driverAbout: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.about_text),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = driverAbout,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfirmation by remember { mutableStateOf(false) }
    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            title = { Text(text = stringResource(R.string.delete_confirmation_title)) },
            text = { Text(text = stringResource(R.string.delete_confirmation_message),
                color = MaterialTheme.colorScheme.secondary) },
            confirmButton = {
                Button(
                    onClick = {
                        onClick()
                        showConfirmation = false
                    }
                ) {
                    Text(stringResource(R.string.confirm_delete))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmation = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    IconButton(
        onClick = { showConfirmation = true },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_button_description),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun DriverScore(score: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_smaller)),
        verticalAlignment = CenterVertically
    ) {
        Text(
            text = score,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = stringResource(R.string.driver_score_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}