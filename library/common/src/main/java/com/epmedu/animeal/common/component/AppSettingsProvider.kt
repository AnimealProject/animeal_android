package com.epmedu.animeal.common.component

import kotlinx.coroutines.flow.Flow

interface AppSettingsProvider {

    suspend fun getAppSettings(): Flow<AppSettings>

    suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit)
}

interface AppSettingsUpdateScope {
    var isInitialGeolocationPermissionRequested: Boolean
}

data class AppSettings(
    val isInitialGeolocationPermissionRequested: Boolean,
)