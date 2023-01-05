package com.epmedu.animeal.tabs.more.faq.presentation

import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion

internal sealed interface FAQScreenEvent {
    data class QuestionClicked(val question: FrequentlyAskedQuestion) : FAQScreenEvent
}