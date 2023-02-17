package com.epmedu.animeal.tabs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.timer.data.model.TimerState
import com.epmedu.animeal.timer.domain.GetTimerStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TabsViewModel @Inject constructor(
    stateDelegate: StateDelegate<TimerState>,
    private val getTimerStateUseCase: GetTimerStateUseCase,
) : ViewModel(),
    StateDelegate<TimerState> by stateDelegate {

    init {
        viewModelScope.launch { getTimerState() }
    }

    private suspend fun getTimerState() {
        getTimerStateUseCase().collect {
            updateState {
                it
            }
        }
    }
}