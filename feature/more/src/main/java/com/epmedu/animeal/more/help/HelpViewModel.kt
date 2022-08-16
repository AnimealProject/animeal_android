package com.epmedu.animeal.more.help

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.state.StateHolder
import com.epmedu.animeal.common.presentation.state.StateHolderImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HelpViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider
) : ViewModel(),
    StateHolder<HelpState> by StateHolderImpl(initialState = HelpState()) {

    init {
        updateState {
            copy(appVersionName = buildConfigProvider.appVersion)
        }
    }
}