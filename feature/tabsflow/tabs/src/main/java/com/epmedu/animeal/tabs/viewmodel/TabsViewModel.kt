package com.epmedu.animeal.tabs.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.presentation.viewmodel.TimerState
import com.epmedu.animeal.tabs.viewmodel.handlers.timer.TimerHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class TabsViewModel @Inject constructor(
    timerHandler: TimerHandler,
    stateDelegate: StateDelegate<TimerState>
) : ViewModel(),
    StateDelegate<TimerState> by stateDelegate,
    TimerHandler by timerHandler