package com.epmedu.animeal.home.data

import com.epmedu.animeal.common.component.AppSettings
import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import com.epmedu.animeal.home.domain.ApplicationSettingsRepository
import kotlinx.coroutines.flow.Flow

class ApplicationSettingsRepositoryImpl(
    private val appSettingsProvider: AppSettingsProvider,
) : ApplicationSettingsRepository {

    override suspend fun getAppSettings(): Flow<AppSettings> {
        return appSettingsProvider.getAppSettings()
    }

    override suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit) {
        appSettingsProvider.updateAppSettings(action)
    }
}