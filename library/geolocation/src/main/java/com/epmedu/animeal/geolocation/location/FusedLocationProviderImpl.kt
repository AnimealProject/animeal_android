package com.epmedu.animeal.geolocation.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.epmedu.animeal.geolocation.location.model.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

internal class FusedLocationProviderImpl(private val client: FusedLocationProviderClient) : LocationProvider {

    @RequiresPermission(ACCESS_FINE_LOCATION)
    override fun fetchUpdates(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.Builder(
            TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL_SECS)
        )
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateIntervalMillis(TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL_SECS))
            .build()

        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                locationResult.lastLocation?.let {
                    val userLocation = Location(
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
