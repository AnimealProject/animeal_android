package com.epmedu.animeal.home.presentation.ui

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.icons.AnimealIcons
import com.epmedu.animeal.foundation.icons.outlined.Geolocation

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
        imageVector = AnimealIcons.Outlined.Geolocation,
        contentDescription = null,
        tint = MaterialTheme.colors.onBackground,
    )
}