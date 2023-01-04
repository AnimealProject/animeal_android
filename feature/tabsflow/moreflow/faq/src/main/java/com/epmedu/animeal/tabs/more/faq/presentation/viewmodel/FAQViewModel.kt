package com.epmedu.animeal.tabs.more.faq.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.tabs.more.faq.domain.GetFAQUseCase
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent.BackClicked
import com.epmedu.animeal.tabs.more.faq.presentation.viewmodel.FAQEvent.NavigateBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FAQViewModel @Inject constructor(
    private val getFAQUseCase: GetFAQUseCase
) : ViewModel(),
    StateDelegate<FAQState> by DefaultStateDelegate(initialState = FAQState()),
    EventDelegate<FAQEvent> by DefaultEventDelegate() {

    init {
        getFAQ()
    }

    private fun getFAQ() {
        viewModelScope.launch {
            getFAQUseCase().collect {
                updateState { copy(questions = it) }
            }
        }
    }

    fun handleEvents(event: FAQScreenEvent) {
        when (event) {
            BackClicked -> navigateBack()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch { sendEvent(NavigateBack) }
    }
}