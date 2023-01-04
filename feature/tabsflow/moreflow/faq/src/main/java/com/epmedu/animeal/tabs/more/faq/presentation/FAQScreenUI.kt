package com.epmedu.animeal.tabs.more.faq.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.layout.LastElementBottom
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent.BackClicked
import com.epmedu.animeal.tabs.more.faq.presentation.ui.FAQHeader
import com.epmedu.animeal.tabs.more.faq.presentation.ui.FAQListItem
import com.epmedu.animeal.tabs.more.faq.presentation.viewmodel.FAQState
import com.epmedu.animeal.tabs.more.faq.util.generateLoremIpsum
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FAQScreenUI(
    state: FAQState,
    onEvent: (FAQScreenEvent) -> Unit
) {
    var selectedQuestion: FrequentlyAskedQuestion? by remember { mutableStateOf(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.LastElementBottom,
    ) {
        item { FAQHeader(onBack = { onEvent(BackClicked) }) }
        items(state.questions) { question ->
            FAQListItem(
                frequentlyAskedQuestion = question,
                isExpanded = question == selectedQuestion,
                onClick = {
                    selectedQuestion = when (question) {
                        selectedQuestion -> null
                        else -> question
                    }
                }
            )
        }
        item {
            Text(
                text = stringResource(id = R.string.faq_footer),
                modifier = Modifier.padding(vertical = 40.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@AnimealPreview
@Composable
private fun FAQScreenUIPreview() {
    AnimealTheme {
        FAQScreenUI(
            state = FAQState(
                questions = List(3) { index ->
                    FrequentlyAskedQuestion(
                        question = "Question ${index + 1}",
                        answer = generateLoremIpsum()
                    )
                }.toImmutableList()
            ),
            onEvent = {},
        )
    }
}
