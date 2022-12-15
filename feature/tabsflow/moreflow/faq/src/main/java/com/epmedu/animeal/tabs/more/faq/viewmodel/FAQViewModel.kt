package com.epmedu.animeal.tabs.more.faq.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class FAQViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider
) : ViewModel(),
    StateDelegate<FAQState> by DefaultStateDelegate(initialState = FAQState()) {

    init {
        updateState {
            copy(appVersionName = buildConfigProvider.appVersion)
        }
    }
}