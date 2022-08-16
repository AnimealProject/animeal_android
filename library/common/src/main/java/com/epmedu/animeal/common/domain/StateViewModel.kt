package com.epmedu.animeal.common.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StateViewModel<State : Any, Event>(initialState: State) : ViewModel() {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    protected val state
        get() = stateFlow.value

    protected fun updateState(reduce: State.() -> State) {
        val newState = state.reduce()
        _stateFlow.value = newState
    }

    protected suspend fun sendEvent(event: Event) {
        _event.emit(event)
    }
}