package com.epmedu.animeal.tabs.more.faq.presentation.model

internal data class FrequentlyAskedQuestionCard(
    val question: String,
    val answer: String,
    val isExpanded: Boolean = false
)