package com.epmedu.animeal.common.presentation.event

import kotlinx.coroutines.flow.SharedFlow

interface EventSource<Event> {

    val events: SharedFlow<Event>

    suspend fun sendEvent(event: Event)
}