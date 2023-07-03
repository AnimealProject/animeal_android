package com.epmedu.animeal.geolocation.gpssetting

import android.content.Context
import androidx.core.location.LocationManagerCompat
import com.epmedu.animeal.extensions.locationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/** Allows to enable/disable gps settings via intent call to the system. */
internal class IntentGpsSettingsProvider(
    @ApplicationContext private val context: Context
) : GpsSettingsProvider {

    private val locationManager = context.locationManager

    override val gpsSettingState: GpsSettingState
        get() = when {
            LocationManagerCompat.isLocationEnabled(locationManager) -> GpsSettingState.Enabled
            else -> GpsSettingState.Disabled
        }

    override fun fetchGpsSettingsUpdates() = callbackFlow {
        val gnssStatusCompat = GnssStatusCompat(
            locationManager = locationManager,
            onGpsStarted = {
                trySend(GpsSettingState.Enabled)
            },
            onGpsStopped = {
                trySend(GpsSettingState.Disabled)
            }
        )

        gnssStatusCompat.registerCallback()

        awaitClose { gnssStatusCompat.unregisterCallback() }
    }
}