package com.epmedu.animeal.geolocation.gpssetting

import android.location.GnssStatus
import android.location.LocationManager
import android.os.Handler
import android.os.Looper

internal class GnssStatusCompat(
    private val locationManager: LocationManager,
    onGpsStarted: () -> Unit,
    onGpsStopped: () -> Unit,
) {
    private val statusListener = GnssStatusCallback(onGpsStarted, onGpsStopped)

    fun registerCallback() {
        locationManager.registerGnssStatusCallback(
            statusListener,
            Handler(Looper.getMainLooper())
        )
    }

    fun unregisterCallback() {
        locationManager.unregisterGnssStatusCallback(statusListener)
    }

    private class GnssStatusCallback(
        private val onGpsStarted: () -> Unit,
        private val onGpsStopped: () -> Unit,
    ) : GnssStatus.Callback() {

        override fun onStarted() = onGpsStarted()
        override fun onStopped() = onGpsStopped()
    }
}