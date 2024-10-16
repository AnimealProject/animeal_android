package com.epmedu.animeal.tabs.more.faq.presentation.viewmodel

import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class FAQState(
    val isLoading: Boolean = true,
    val selectedQuestion: FrequentlyAskedQuestion? = null,
    val questions: ImmutableList<FrequentlyAskedQuestion> = persistentListOf()
)