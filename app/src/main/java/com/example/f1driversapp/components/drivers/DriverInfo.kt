package com.example.f1driversapp.components.drivers


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.f1driversapp.R

@Composable
fun DriverInfo(name: String, squad: String) {
    Column(
        modifier = Modifier
            .padding(start = dimensionResource(R.dimen.padding_small))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
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
fun DriverAbout(
    driverAbout: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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
fun DriverScore(score: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_smaller)),
        verticalAlignment = Alignment.CenterVertically
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