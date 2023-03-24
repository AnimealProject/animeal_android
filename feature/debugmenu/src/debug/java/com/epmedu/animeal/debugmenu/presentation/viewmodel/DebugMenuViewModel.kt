package com.epmedu.animeal.debugmenu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.debugmenu.domain.DebugMenuRepository
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent.SwitchUsingMockedFeedingPoints
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DebugMenuViewModel @Inject constructor(
    private val repository: DebugMenuRepository
) : ViewModel() {

    fun handleEvents(event: DebugMenuScreenEvent) {
        when (event) {
            is SwitchUsingMockedFeedingPoints -> {
                repository.useMockedFeedingPoints = event.useMockedFeedingPoint
            }
        }
    }
}