package com.epmedu.animeal.component

import android.annotation.SuppressLint
import android.os.Looper
import com.epmedu.animeal.common.component.LocationProvider
import com.epmedu.animeal.common.data.model.MapLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

@SuppressLint("MissingPermission")
class LocationProviderImpl(private val client: FusedLocationProviderClient) : LocationProvider {

    override fun fetchUpdates(): Flow<MapLocation> = callbackFlow {

        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL_SECS)
            fastestInterval = TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL_SECS)
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                locationResult.lastLocation?.let {

                    val userLocation = MapLocation(
                        latitude = it.latitude,
                        longitude = it.longitude,
                    )

                    trySend(userLocation)
                }
            }
        }

        client.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())
        awaitClose { client.removeLocationUpdates(callBack) }
    }

    companion object {
        private const val UPDATE_INTERVAL_SECS = 10L
        private const val FASTEST_UPDATE_INTERVAL_SECS = 2L
    }
}