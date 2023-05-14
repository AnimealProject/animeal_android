package com.epmedu.animeal.feeding.presentation.viewmodel.handler

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.usecase.MotivateUseGpsSettingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateMotivateUseGpsSettingsUseCase
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedConfirmationDialogState
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class DefaultWillFeedHandler(
    stateDelegate: StateDelegate<WillFeedState>,
    private val alreadyMotivateUseGpsLocationUseCase: MotivateUseGpsSettingUseCase,
    private val updateMotivateUseGpsLocationSettingsUseCase: UpdateMotivateUseGpsSettingsUseCase,
    private val gpsSettingsProvider: GpsSettingsProvider
) : WillFeedHandler, StateDelegate<WillFeedState> by stateDelegate {

    private var isLocationAsked = false

    private var locationJob: Job? = null

    private val scope = CoroutineScope(Dispatchers.Main)

    override var willFeedStateFlow: StateFlow<WillFeedState> = stateFlow

    override fun CoroutineScope.registerWillFeedState(updateCall: (WillFeedState) -> Unit) {
        launch {
            willFeedStateFlow.collectLatest { updateCall(it) }
        }
    }

    override fun handleWillFeedEvent(event: WillFeedEvent) {
        when (event) {
            WillFeedEvent.DismissWillFeedDialog -> dismissWillFeedDialog()
            WillFeedEvent.ShowWillFeedDialog -> handleStartFeeding()
            WillFeedEvent.DeclineUseGps -> handleDeclineUseGps()
            WillFeedEvent.OpenGpsSettings -> handleOpenGpsSettings()
            WillFeedEvent.EmbeddedDialogClosed -> {
                locationJob?.let {
                    updateState { copy(showLocationSettingsEmbeddedDialog = false) }
                    showWillFeedDialog()
                    it.cancel()
                    locationJob = null
                }
            }
        }
    }

    private fun handleDeclineUseGps() {
        scope.launch {
            hideMotivateUseGpsDialog()
            updateMotivateUseGpsLocationSettingsUseCase(true)
            showWillFeedDialog()
        }
    }

    private fun handleStartFeeding() {
        scope.launch {
            if (gpsSettingsProvider.isGpsPermissionGranted) {
                if (gpsSettingsProvider.isGpsSettingsEnabled || isLocationAsked) {
                    showWillFeedDialog()
                } else {
                    showUseEmbeddedLocationSettingsDialog()
                }
            } else {
                if (alreadyMotivateUseGpsLocationUseCase()) {
                    showWillFeedDialog()
                } else {
                    showMotivateUseGpsDialog()
                }
            }
        }
    }

    private fun handleOpenGpsSettings() {
        scope.launch {
            gpsSettingsProvider.fetchGpsPermissionGranted()
                .filter { it }
                .collect {
                    updateState { copy(openGpsSettings = false) }
                    showWillFeedDialog()
                    cancel()
                }
        }
        hideMotivateUseGpsDialog()
        updateState { copy(openGpsSettings = true) }
    }

    private fun showUseEmbeddedLocationSettingsDialog() {
        locationJob = scope.launch {
            gpsSettingsProvider.fetchGpsSettingsUpdates()
                .filter { it is GpsSettingsProvider.GpsSettingState.Enabled }
                .collect {
                    updateState { copy(showLocationSettingsEmbeddedDialog = false) }
                    showWillFeedDialog()
                    cancel()
                }
        }

        isLocationAsked = true
        updateState {
            copy(showLocationSettingsEmbeddedDialog = true)
        }
    }

    private fun showMotivateUseGpsDialog() {
        updateState {
            copy(showMotivateUseGpsDialog = true)
        }
    }

    private fun hideMotivateUseGpsDialog() {
        updateState {
            copy(showMotivateUseGpsDialog = false)
        }
    }

    private fun showWillFeedDialog() {
        updateState { copy(feedConfirmationDialog = FeedConfirmationDialogState.Showing) }
    }

    private fun dismissWillFeedDialog() {
        updateState { copy(feedConfirmationDialog = FeedConfirmationDialogState.Dismissed) }
    }
}