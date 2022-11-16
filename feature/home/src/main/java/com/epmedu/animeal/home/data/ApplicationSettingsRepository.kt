package com.epmedu.animeal.home.data

import com.epmedu.animeal.common.component.AppSettings
import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import kotlinx.coroutines.flow.Flow

interface ApplicationSettingsRepository {

    suspend fun getAppSettings(): Flow<AppSettings>

    suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit)
}
