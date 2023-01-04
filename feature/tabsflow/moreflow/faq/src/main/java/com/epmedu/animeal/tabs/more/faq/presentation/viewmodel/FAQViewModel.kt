package com.epmedu.animeal.tabs.more.faq.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.tabs.more.faq.domain.GetFAQUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FAQViewModel @Inject constructor(
    private val getFAQUseCase: GetFAQUseCase
) : ViewModel(),
    StateDelegate<FAQState> by DefaultStateDelegate(initialState = FAQState()) {

    init {
        viewModelScope.launch {
            getFAQUseCase().collect {
                updateState { copy(questions = it) }
            }
        }
    }
}