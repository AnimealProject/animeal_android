package com.epmedu.animeal.more.help

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.domain.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HelpViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider
) : StateViewModel<HelpState>(initialState = HelpState()) {

    init {
        updateState {
            copy(appVersionName = buildConfigProvider.appVersion)
        }
    }
}