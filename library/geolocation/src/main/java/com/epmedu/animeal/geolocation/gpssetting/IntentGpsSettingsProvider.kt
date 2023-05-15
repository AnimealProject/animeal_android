package com.epmedu.animeal.geolocation.gpssetting

import android.content.Context
import androidx.core.location.LocationManagerCompat
import com.epmedu.animeal.extensions.locationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update

/** Allows to enable/disable gps settings via intent call to the system */
internal class IntentGpsSettingsProvider(@ApplicationContext private val context: Context) : GpsSettingsProvider {

    private val locationManager = context.locationManager

    private var _isGpsPermissionGranted = MutableStateFlow(false)

    override val isGpsSettingsEnabled: Boolean
        get() = LocationManagerCompat.isLocationEnabled(locationManager)

    override val isGpsPermissionGranted: Boolean
        get() = _isGpsPermissionGranted.value
    override fun fetchGpsSettingsUpdates() = callbackFlow {
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
    override fun fetchGpsPermissionGranted(): Flow<Boolean> = _isGpsPermissionGranted.asStateFlow()

    override fun setGpsPermissionGranted(isGranted: Boolean) {
        _isGpsPermissionGranted.update { isGranted }
    }
}