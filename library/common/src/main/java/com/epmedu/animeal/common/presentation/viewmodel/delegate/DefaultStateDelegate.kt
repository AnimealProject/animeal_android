package com.epmedu.animeal.common.presentation.viewmodel.delegate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultStateDelegate<State>(initialState: State) : StateDelegate<State> {

    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow = _stateFlow.asStateFlow()

    override val state
        get() = stateFlow.value

    override fun updateState(reduce: State.() -> State) {
        _stateFlow.update(reduce)
    }
}