@file:Suppress("DEPRECATION")

package com.epmedu.animeal.geolocation.gpssetting

import android.location.GnssStatus
import android.location.GpsStatus
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper

@Suppress("IMPLICIT_CAST_TO_ANY")
internal class GnssStatusCompat(
    private val locationManager: LocationManager,
    onGpsStarted: () -> Unit,
    onGpsStopped: () -> Unit,
) {
    private val statusListener = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        GnssStatusCallback(onGpsStarted, onGpsStopped)
    } else {
        GpsStatusListener(onGpsStarted, onGpsStopped)
    }

    fun registerCallback() {
        when (statusListener) {
            is GpsStatus.Listener -> {
                locationManager.addGpsStatusListener(statusListener)
            }
            is GnssStatus.Callback -> {
                locationManager.registerGnssStatusCallback(
                    statusListener,
                    Handler(Looper.getMainLooper())
                )
            }
        }
    }

    fun unregisterCallback() {
        when (statusListener) {
            is GpsStatus.Listener -> {
                locationManager.removeGpsStatusListener(statusListener)
            }
            is GnssStatus.Callback -> {
                locationManager.unregisterGnssStatusCallback(statusListener)
            }
        }
    }

    /** GpsSetting status callback for api >=24 */
    private class GnssStatusCallback(
        private val onGpsStarted: () -> Unit,
        private val onGpsStopped: () -> Unit,
    ) : GnssStatus.Callback() {

        override fun onStarted() = onGpsStarted()
        override fun onStopped() = onGpsStopped()
    }

    /** GpsSetting status callback for api <24 */
    private class GpsStatusListener(
        private val onGpsStarted: () -> Unit,
        private val onGpsStopped: () -> Unit,
    ) : GpsStatus.Listener {

        override fun onGpsStatusChanged(event: Int) {
            when (event) {
                GpsStatus.GPS_EVENT_STARTED -> onGpsStarted()
                GpsStatus.GPS_EVENT_STOPPED -> onGpsStopped()
            }
        }
    }
}