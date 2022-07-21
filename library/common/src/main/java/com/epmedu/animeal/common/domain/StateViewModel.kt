package com.epmedu.animeal.common.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class StateViewModel<Event : Any, State : Any>(initialState: State) : ViewModel() {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    protected val state
        get() = stateFlow.value

    private val _event = MutableSharedFlow<Event>()

    init {
        subscribeToEvents()
    }

    abstract fun handleEvents(event: Event)

    protected fun updateState(reduce: State.() -> State) {
        val newState = state.reduce()
        _stateFlow.value = newState
    }

    fun onEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }
}