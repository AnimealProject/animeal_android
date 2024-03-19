package com.epmedu.animeal.tabs.more.faq.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.tabs.more.faq.domain.GetFAQUseCase
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent.QuestionClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FAQViewModel @Inject constructor(
    private val getFAQUseCase: GetFAQUseCase
) : ViewModel(),
    StateDelegate<FAQState> by DefaultStateDelegate(initialState = FAQState()) {

    init {
        getFaq()
    }

    private fun getFaq() {
        viewModelScope.launch {
            getFAQUseCase().collect {
                updateState { copy(questions = it, isLoading = false) }
            }
        }
    }

    fun handleEvents(event: FAQScreenEvent) {
        when (event) {
            is QuestionClicked -> updateSelectedQuestion(event.question)
        }
    }

    private fun updateSelectedQuestion(clickedQuestion: FrequentlyAskedQuestion) {
        updateState {
            copy(
                selectedQuestion = when (clickedQuestion) {
                    state.selectedQuestion -> null
                    else -> clickedQuestion
                }
            )
        }
    }
}