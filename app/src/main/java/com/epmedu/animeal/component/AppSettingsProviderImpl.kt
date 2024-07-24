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

    override fun getAppSettings(): Flow<AppSettings> {
        return dataStore.data.map { preferences -> preferences.toAppSettings() }
    }

    override suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit) {
        dataStore.edit {
            val scope = AppSettingsUpdateScopeImpl(toAppSettings()).apply(action)

            with(scope.toAppSettings()) {
                updateIsGeolocationPermissionRationaleShown(isGeolocationPermissionRationaleShown)
                updateAnimalType(animalType)
                updateViewedFeedingIds(viewedFeedingIds)
            }
        }
    }

    private fun Preferences.toAppSettings() = AppSettings(
        isGeolocationPermissionRationaleShown = isGeolocationPermissionRationaleShown,
        animalType = animalType,
        viewedFeedingIds = viewedFeedingIds
    )
}

private class AppSettingsUpdateScopeImpl(
    override var isGeolocationPermissionRationaleShown: Boolean,
    override var animalType: String,
    override var viewedFeedingIds: Set<String>
) : AppSettingsUpdateScope {

    constructor(settings: AppSettings) : this(
        settings.isGeolocationPermissionRationaleShown,
        settings.animalType,
        settings.viewedFeedingIds
    )

    fun toAppSettings() = AppSettings(
        isGeolocationPermissionRationaleShown = isGeolocationPermissionRationaleShown,
        animalType = animalType,
        viewedFeedingIds = viewedFeedingIds
    )
}
