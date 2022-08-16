package com.epmedu.animeal.common.presentation.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateHolderImpl<State>(initialState: State) : StateHolder<State> {

    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow = _stateFlow.asStateFlow()

    override val state
        get() = stateFlow.value

    override fun updateState(reduce: State.() -> State) {
        val newState = state.reduce()
        _stateFlow.value = newState
    }
}