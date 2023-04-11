package com.epmedu.animeal.component

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.epmedu.animeal.common.component.AppSettings
import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AppSettingsProviderImpl(
    private val dataStore: DataStore<Preferences>,
) : AppSettingsProvider {

    private val initialGeolocationPermission =
        booleanPreferencesKey("InitialGeolocationPermissionOnHomeScreen")

    private val animalType = stringPreferencesKey("animalType")

    override suspend fun getAppSettings(): Flow<AppSettings> {
        return dataStore.data.map { preferences -> preferences.toAppSettings() }
    }

    override suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit) {
        dataStore.edit { preferences ->
            val scope = AppSettingsUpdateScopeImpl(preferences.toAppSettings()).apply(action)
            val settings = scope.toAppSettings()
            preferences.setInitialGeolocationPermissionRequest(settings.isInitialGeolocationPermissionRequested)
            preferences.setAnimalType(settings.animalType)
        }
    }

    private fun Preferences.toAppSettings() = AppSettings(
        isInitialGeolocationPermissionRequested = getInitialGeolocationPermissionRequest(),
        animalType = getAnimalType()
    )

    private fun Preferences.getInitialGeolocationPermissionRequest(): Boolean {
        return get(initialGeolocationPermission) ?: false
    }

    private fun Preferences.getAnimalType(): String {
        return get(animalType) ?: ""
    }

    private fun MutablePreferences.setInitialGeolocationPermissionRequest(value: Boolean) {
        set(initialGeolocationPermission, value)
    }

    private fun MutablePreferences.setAnimalType(value: String) {
        set(animalType, value)
    }
}

private class AppSettingsUpdateScopeImpl(
    override var isInitialGeolocationPermissionRequested: Boolean,
    override var animalType: String,
) : AppSettingsUpdateScope {

    constructor(settings: AppSettings) : this(settings.isInitialGeolocationPermissionRequested, settings.animalType)

    fun toAppSettings() = AppSettings(
        isInitialGeolocationPermissionRequested = isInitialGeolocationPermissionRequested,
        animalType = animalType
    )
}
