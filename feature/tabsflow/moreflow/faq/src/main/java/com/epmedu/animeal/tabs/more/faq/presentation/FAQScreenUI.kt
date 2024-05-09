package com.epmedu.animeal.tabs.more.faq.presentation

import androidx.compose.runtime.Composable
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.util.generateLoremIpsum
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import com.epmedu.animeal.tabs.more.faq.presentation.ui.FAQContent
import com.epmedu.animeal.tabs.more.faq.presentation.ui.FAQEmptyState
import com.epmedu.animeal.tabs.more.faq.presentation.ui.FAQLoadingState
import com.epmedu.animeal.tabs.more.faq.presentation.viewmodel.FAQState
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FAQScreenUI(
    state: FAQState,
    onBack: () -> Unit,
    onEvent: (FAQScreenEvent) -> Unit
) {
    when {
        state.isLoading -> {
            FAQLoadingState(onBack)
        }
        state.questions.isEmpty() -> {
            FAQEmptyState(onBack)
        }
        else -> {
            FAQContent(state, onBack, onEvent)
        }
    }
}

@AnimealPreview
@Composable
private fun FAQScreenUIPreview() {
    AnimealTheme {
        FAQScreenUI(
            state = FAQState(
                isLoading = false,
                questions = List(3) { index ->
                    FrequentlyAskedQuestion(
                        question = "Question ${index + 1}",
                        answer = generateLoremIpsum()
                    )
                }.toImmutableList()
            ),
            onBack = {},
            onEvent = {}
        )
    }
}

@AnimealPreview
@Composable
private fun FAQScreenUIEmptyStatePreview() {
    AnimealTheme {
        FAQScreenUI(
            state = FAQState(
                isLoading = false
            ),
            onBack = {},
            onEvent = {}
        )
    }
}

@AnimealPreview
@Composable
private fun FAQScreenUILoadingStatePreview() {
    AnimealTheme {
        FAQScreenUI(
            state = FAQState(),
            onBack = {},
            onEvent = {}
        )
    }
}
