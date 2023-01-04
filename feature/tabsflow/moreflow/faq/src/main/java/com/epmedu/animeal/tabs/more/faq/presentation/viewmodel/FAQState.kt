package com.epmedu.animeal.tabs.more.faq.presentation.viewmodel

import com.epmedu.animeal.tabs.more.faq.domain.model.FAQCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class FAQState(
    val questionCards: ImmutableList<FAQCard> = persistentListOf()
)