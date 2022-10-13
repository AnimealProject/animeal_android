package com.epmedu.animeal.home.presentation.model

import androidx.compose.runtime.Stable

@Stable
sealed interface GpsSettingState {

    object Enabled : GpsSettingState

    object Disabled : GpsSettingState
}
