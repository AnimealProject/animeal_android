package com.epmedu.animeal.tabs.more.faq.presentation

import com.epmedu.animeal.tabs.more.faq.presentation.model.FAQCard

internal sealed interface FAQScreenEvent {
    object BackClicked : FAQScreenEvent
    data class CardClicked(val card: FAQCard) : FAQScreenEvent
}