package com.epmedu.animeal.common.presentation.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventSourceImpl<Event> : EventSource<Event> {

    private val _events = MutableSharedFlow<Event>()
    override val events = _events.asSharedFlow()

    override suspend fun sendEvent(event: Event) {
        _events.emit(event)
    }
}