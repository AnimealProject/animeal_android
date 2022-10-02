package com.epmedu.animeal.geolocation.gpssetting

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.provider.Settings
import androidx.core.location.LocationManagerCompat
import com.epmedu.animeal.extensions.locationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/** Allows to enable/disable gps settings via intent call to the system */
internal class IntentGpsSettingsProvider(@ApplicationContext private val context: Context) : GpsSettingsProvider {

    private val locationManager = context.locationManager

    override val isGpsSettingsEnabled: Boolean
        get() = LocationManagerCompat.isLocationEnabled(locationManager)

    // FLAG_ACTIVITY_NEW_TASK is required when we're starting activity from app context
    override fun changeGpsSettings() = context.startActivity(
        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(FLAG_ACTIVITY_NEW_TASK)
    )

    override fun fetchUpdates() = callbackFlow {
        val gnssStatusCompat = GnssStatusCompat(
            locationManager = locationManager,
            onGpsStarted = {
                trySend(GpsSettingsProvider.GpsSettingState.Enabled)
            },
            onGpsStopped = {
                trySend(GpsSettingsProvider.GpsSettingState.Disabled)
            }
        )

        gnssStatusCompat.registerCallback()

        awaitClose { gnssStatusCompat.unregisterCallback() }
    }
}