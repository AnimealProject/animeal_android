package com.epmedu.animeal.common.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StateViewModel<State : Any>(initialState: State) : ViewModel() {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    protected val state
        get() = stateFlow.value

    protected fun updateState(reduce: State.() -> State) {
        val newState = state.reduce()
        _stateFlow.value = newState
    }
}