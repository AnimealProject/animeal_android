package com.epmedu.animeal.tabs.more.faq.presentation.viewmodel

import com.epmedu.animeal.tabs.more.faq.presentation.model.FrequentlyAskedQuestionCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class FAQState(
    val questionCards: ImmutableList<FrequentlyAskedQuestionCard> = persistentListOf()
)