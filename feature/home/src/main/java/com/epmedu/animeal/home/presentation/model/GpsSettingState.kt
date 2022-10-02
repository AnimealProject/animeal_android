package com.epmedu.animeal.home.presentation.model

sealed interface GpsSettingState {

    object Enabled : GpsSettingState

    object Disabled : GpsSettingState
}
