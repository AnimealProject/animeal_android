package com.epmedu.animeal.network

import kotlinx.coroutines.flow.Flow

interface NetworkStateProvider {
    fun getNetworkState(): Flow<NetworkState>
}