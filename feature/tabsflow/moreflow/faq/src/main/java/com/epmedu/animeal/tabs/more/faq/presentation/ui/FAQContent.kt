package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.layout.LastElementBottom
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.util.generateLoremIpsum
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent
import com.epmedu.animeal.tabs.more.faq.presentation.viewmodel.FAQState
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FAQContent(
    state: FAQState,
    onBack: () -> Unit,
    onEvent: (FAQScreenEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.LastElementBottom,
    ) {
        item { FAQHeader(onBack = onBack) }
        items(state.questions) { question ->
            FAQListItem(
                frequentlyAskedQuestion = question,
                isExpanded = question == state.selectedQuestion,
                onClick = { onEvent(FAQScreenEvent.QuestionClicked(question)) }
            )
        }
        item {
            SelectionContainer {
                Text(
                    text = stringResource(id = R.string.faq_footer),
                    modifier = Modifier.padding(vertical = 40.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun FAQContentPreview() {
    AnimealTheme {
        FAQContent(
            state = FAQState(
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