package com.epmedu.animeal.debugmenu.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.debugmenu.domain.DebugMenuRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

internal class DebugMenuRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DebugMenuRepository {

    private val useMockedFeedingPointsKey = booleanPreferencesKey("use_mocked_feeding_points")

    override var useMockedFeedingPoints: Boolean
        get() {
            return runBlocking {
                dataStore.data.firstOrNull()?.get(useMockedFeedingPointsKey) ?: false
            }
        }
        set(value) {
            runBlocking {
                dataStore.edit { preferences ->
                    preferences[useMockedFeedingPointsKey] = value
                }
            }
        }
}