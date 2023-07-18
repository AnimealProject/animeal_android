package com.epmedu.animeal.home.presentation.viewmodel.handlers

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

internal class DefaultErrorHandler(
    stateDelegate: StateDelegate<HomeState>,
) : ErrorHandler,
    StateDelegate<HomeState> by stateDelegate {

    override fun showError() {
        updateState { copy(isError = true) }
    }

    override fun hideError() {
        updateState { copy(isError = false) }
    }
}