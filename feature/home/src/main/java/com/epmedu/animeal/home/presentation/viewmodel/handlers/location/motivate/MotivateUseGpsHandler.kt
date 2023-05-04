package com.epmedu.animeal.home.presentation.viewmodel.handlers.location.motivate

import com.epmedu.animeal.home.presentation.HomeScreenEvent.MotivateUseGpsEvent

internal interface MotivateUseGpsHandler {

    val motivateUseGps: Boolean

    fun handleMotivationEvents(event: MotivateUseGpsEvent)
}