package com.epmedu.animeal.component

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.common.component.AppSettings
import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.initialCameraPermissionKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.initialGeolocationPermissionKey
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
        }
    }

    private fun Preferences.toAppSettings() = AppSettings(
        isInitialGeolocationPermissionRequested = getInitialGeolocationPermissionRequest(),
        isCameraPermissionRequested = getCameraPermissionRequested()
    )

    private fun Preferences.getInitialGeolocationPermissionRequest(): Boolean {
        return get(initialGeolocationPermissionKey) ?: false
    }

    private fun MutablePreferences.setInitialGeolocationPermissionRequest(value: Boolean) {
        set(initialGeolocationPermissionKey, value)
    }

    private fun Preferences.getCameraPermissionRequested(): Boolean {
        return get(initialCameraPermissionKey) ?: false
    }

    private fun MutablePreferences.setCameraPermissionRequested(value: Boolean) {
        set(initialCameraPermissionKey, value)
    }
}

private class AppSettingsUpdateScopeImpl(
    override var isInitialGeolocationPermissionRequested: Boolean,
    override var isCameraPermissionRequested: Boolean,
) : AppSettingsUpdateScope {

    constructor(settings: AppSettings) : this(
        settings.isInitialGeolocationPermissionRequested,
        settings.isCameraPermissionRequested
    )

    fun toAppSettings() = AppSettings(
        isInitialGeolocationPermissionRequested = isInitialGeolocationPermissionRequested,
        isCameraPermissionRequested = isCameraPermissionRequested
    )
}
