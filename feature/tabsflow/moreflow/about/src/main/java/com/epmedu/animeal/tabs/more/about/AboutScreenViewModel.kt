package com.epmedu.animeal.tabs.more.about

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class AboutScreenViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
) : ViewModel(),
    StateDelegate<AboutScreenState> by DefaultStateDelegate(AboutScreenState()) {
    init {
        updateState {
            copy(currentVersion = buildConfigProvider.appVersion)
        }
    }
}