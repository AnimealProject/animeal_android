package com.epmedu.animeal.common.domain

import com.epmedu.animeal.common.component.AppSettings
import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import kotlinx.coroutines.flow.Flow

interface ApplicationSettingsRepository {

    fun getAppSettings(): Flow<AppSettings>

    suspend fun updateAppSettings(action: AppSettingsUpdateScope.() -> Unit)
}
