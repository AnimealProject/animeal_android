package com.epmedu.animeal.home.presentation.ui

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.epmedu.animeal.resources.R

@Composable
fun GeoLocationFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = FloatingActionButton(
    modifier = modifier,
    onClick = onClick,
    backgroundColor = MaterialTheme.colors.background,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_geolocation),
        contentDescription = null,
        tint = MaterialTheme.colors.onBackground,
    )
}