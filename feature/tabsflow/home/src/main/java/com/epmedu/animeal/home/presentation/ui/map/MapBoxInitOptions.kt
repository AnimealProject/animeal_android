package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class MapBoxInitOptions(
    val publicKey: String,
    val styleUrl: String
)

@Composable
fun rememberMapInitOptions(mapInitOptions: MapBoxInitOptions) = remember { mapInitOptions }