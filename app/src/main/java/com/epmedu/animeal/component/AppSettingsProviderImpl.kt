package com.epmedu.animeal.component

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.common.component.AppSettings
import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.animalType
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.initialCameraPermissionKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.initialGeolocationPermissionKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.initialMotivateUseGps
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AppSettingsProviderImpl(
    private val dataStore: DataStore<Preferences>,
) : AppSettingsProvider {

    override suspend fun getAppSettings(): Flow<AppSettings> {
        return dataStore.data.map { preferences -> preferences.toAppSettings() }
    }

    override suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit) {
        dataStore.edit { preferences ->
            val scope = AppSettingsUpdateScopeImpl(preferences.toAppSettings()).apply(action)
            val settings = scope.toAppSettings()
            preferences.setInitialGeolocationPermissionRequest(settings.isInitialGeolocationPermissionRequested)
            preferences.setCameraPermissionRequested(settings.isCameraPermissionRequested)
            preferences.setAnimalType(settings.animalType)
            preferences.setMotivatedUseGps(settings.isMotivatedUseGps)
        }
    }

    private fun Preferences.toAppSettings() = AppSettings(
        isInitialGeolocationPermissionRequested = getInitialGeolocationPermissionRequest(),
        isCameraPermissionRequested = getCameraPermissionRequested(),
        animalType = getAnimalType(),
        isMotivatedUseGps = getMotivatedUseGps()
    )

    private fun Preferences.getInitialGeolocationPermissionRequest(): Boolean {
        return get(initialGeolocationPermissionKey) ?: false
    }

    private fun Preferences.getAnimalType(): String {
        return get(animalType).orEmpty()
    }

    private fun MutablePreferences.setInitialGeolocationPermissionRequest(value: Boolean) {
        set(initialGeolocationPermissionKey, value)
    }

    private fun Preferences.getCameraPermissionRequested(): Boolean {
        return get(initialCameraPermissionKey) ?: false
    }

    private fun Preferences.getMotivatedUseGps(): Boolean {
        return get(initialMotivateUseGps) ?: false
    }

    private fun MutablePreferences.setCameraPermissionRequested(value: Boolean) {
        set(initialCameraPermissionKey, value)
    }

    private fun MutablePreferences.setAnimalType(value: String) {
        set(animalType, value)
    }

    private fun MutablePreferences.setMotivatedUseGps(value: Boolean) {
        set(initialMotivateUseGps, value)
    }
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
