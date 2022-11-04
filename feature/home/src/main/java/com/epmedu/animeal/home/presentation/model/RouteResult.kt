package com.epmedu.animeal.home.presentation.model

data class RouteResult(
    val isSuccessful: Boolean,
    val timeLeft: String? = null,
    val distanceLeft: String? = null,
    val errorMessage: String? = null
)
