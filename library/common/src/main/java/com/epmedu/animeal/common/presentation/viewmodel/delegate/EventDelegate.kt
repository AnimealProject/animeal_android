package com.epmedu.animeal.common.presentation.viewmodel.delegate

import kotlinx.coroutines.flow.SharedFlow

interface EventDelegate<Event> {

    val events: SharedFlow<Event>

    suspend fun sendEvent(event: Event)
}