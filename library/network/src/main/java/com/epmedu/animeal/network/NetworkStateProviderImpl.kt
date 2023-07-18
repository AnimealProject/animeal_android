package com.epmedu.animeal.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkStateProviderImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkStateProvider {
    override fun getWifiState(): Flow<NetworkState> {
        return flow {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(
                connectivityManager.activeNetwork
            )
            when {
                networkCapabilities == null -> emit(NetworkState.Lost)
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> emit(
                    NetworkState.Available
                )
                else -> emit(NetworkState.Lost)
            }
        }
    }
}