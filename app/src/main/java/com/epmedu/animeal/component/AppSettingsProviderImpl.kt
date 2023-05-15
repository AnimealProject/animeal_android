package com.epmedu.animeal.component

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.common.component.AppSettings
import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import com.epmedu.animeal.extensions.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AppSettingsProviderImpl(
    private val dataStore: DataStore<Preferences>,
) : AppSettingsProvider {

    override suspend fun getAppSettings(): Flow<AppSettings> {
        return dataStore.data.map { preferences -> preferences.toAppSettings() }
    }

    override suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit) {
        dataStore.edit {
            val scope = AppSettingsUpdateScopeImpl(toAppSettings()).apply(action)
            val settings = scope.toAppSettings()
            updateInitialGeolocationPermission(settings.isInitialGeolocationPermissionRequested)
            updateInitialCameraPermission(settings.isCameraPermissionRequested)
            updateAnimalType(settings.animalType)
            updateInitialCameraPermission(settings.isMotivatedUseGps)
        }
    }

    private fun Preferences.toAppSettings() = AppSettings(
        isInitialGeolocationPermissionRequested = initialGeolocationPermission,
        isCameraPermissionRequested = initialCameraPermission,
        animalType = animalType,
        isMotivatedUseGps = motivatedUseGps
    )
}

private class AppSettingsUpdateScopeImpl(
    override var isInitialGeolocationPermissionRequested: Boolean,
    override var isCameraPermissionRequested: Boolean,
    override var animalType: String,
    override var isMotivatedUseGps: Boolean,
) : AppSettingsUpdateScope {

    constructor(settings: AppSettings) : this(
        settings.isInitialGeolocationPermissionRequested,
        settings.isCameraPermissionRequested,
        settings.animalType,
        settings.isMotivatedUseGps
    )

    fun toAppSettings() = AppSettings(
        isInitialGeolocationPermissionRequested = isInitialGeolocationPermissionRequested,
        isCameraPermissionRequested = isCameraPermissionRequested,
        animalType = animalType,
        isMotivatedUseGps = isMotivatedUseGps
    )
}
