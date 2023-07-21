package com.epmedu.animeal.splash.presentation.viewmodel

import com.epmedu.animeal.network.NetworkState

internal data class SplashState(
    val nextDestination: SplashNextDestination? = null,
    val networkState: NetworkState = NetworkState.Available,
    val isError: Boolean = false
)
