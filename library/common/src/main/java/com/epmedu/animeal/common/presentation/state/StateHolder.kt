package com.epmedu.animeal.common.presentation.state

import kotlinx.coroutines.flow.StateFlow

interface StateHolder<State> {

    val stateFlow: StateFlow<State>

    val state: State

    fun updateState(reduce: State.() -> State)
}