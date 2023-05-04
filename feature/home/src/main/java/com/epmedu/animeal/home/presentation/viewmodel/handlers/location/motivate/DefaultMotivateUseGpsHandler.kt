package com.epmedu.animeal.home.presentation.viewmodel.handlers.location.motivate

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.presentation.HomeScreenEvent.MotivateUseGpsEvent
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

internal class DefaultMotivateUseGpsHandler(
    stateDelegate: StateDelegate<HomeState>
) : MotivateUseGpsHandler,
    StateDelegate<HomeState> by stateDelegate {

    private var _motivateUseGps = false

    override val motivateUseGps: Boolean
        get() = _motivateUseGps

    override fun handleMotivationEvents(event: MotivateUseGpsEvent) {
        when (event) {
            MotivateUseGpsEvent.AskUseGps -> handleAskPermission()
            MotivateUseGpsEvent.DeclineUseGps -> handleDeclinePermission()
            MotivateUseGpsEvent.ShowMotivateDialog -> openMotivateUseGpsDialog()
            MotivateUseGpsEvent.PermissionsSuccessfullyGets -> _motivateUseGps = false
        }
    }

    private fun handleAskPermission() {
        closeDialog()
        updateState {
            copy(
                isInitialGeolocationPermissionAsked = false
            )
        }
    }

    private fun openMotivateUseGpsDialog() {
        _motivateUseGps = true
        updateState {
            copy(showMotivateUseGpsDialog = true)
        }
    }

    private fun handleDeclinePermission() {
        _motivateUseGps = false
        closeDialog()
    }

    private fun closeDialog() {
        updateState {
            copy(showMotivateUseGpsDialog = false)
        }
    }
}