package com.epmedu.animeal.tabs.more.faq.domain.model

internal data class FAQCard(
    val question: String,
    val answer: String,
    val isSelected: Boolean = false
)