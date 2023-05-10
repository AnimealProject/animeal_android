package com.epmedu.animeal.common.component

import kotlinx.coroutines.flow.Flow

interface AppSettingsProvider {

    suspend fun getAppSettings(): Flow<AppSettings>

    suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit)
}

interface AppSettingsUpdateScope {
    var isInitialGeolocationPermissionRequested: Boolean
    var isCameraPermissionRequested: Boolean
    var animalType: String
    var isMotivatedUseGps: Boolean
}

data class AppSettings(
    val isInitialGeolocationPermissionRequested: Boolean,
    val isCameraPermissionRequested: Boolean,
    val animalType: String,
    val isMotivatedUseGps: Boolean
)