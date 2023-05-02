package com.epmedu.animeal.home.presentation.viewmodel.handlers.error

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

internal class DefaultErrorHandler(
    stateDelegate: StateDelegate<HomeState>,
) : ErrorHandler,
    StateDelegate<HomeState> by stateDelegate {

    override fun showError(message: String) {
        updateState { copy(errorMessage = message) }
    }

    override fun hideError() {
        updateState { copy(errorMessage = null) }
    }
}