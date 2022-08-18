package com.epmedu.animeal.common.presentation.viewmodel.delegate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultStateDelegate<State>(initialState: State) : StateDelegate<State> {

    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow = _stateFlow.asStateFlow()

    override val state
        get() = stateFlow.value

    override fun updateState(reduce: State.() -> State) {
        val newState = state.reduce()
        _stateFlow.value = newState
    }
}