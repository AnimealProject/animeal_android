package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class MapUiSettings(
    val scalebar: Boolean = true,
    val userLocationOnMap: Boolean = true,
    val locationPulsing: Boolean = true,
)

@Composable
fun rememberMapUiSettings(mapUiSettings: MapUiSettings): MapUiSettings = remember { mapUiSettings }