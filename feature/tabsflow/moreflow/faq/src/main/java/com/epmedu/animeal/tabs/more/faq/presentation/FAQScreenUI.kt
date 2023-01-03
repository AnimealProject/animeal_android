package com.epmedu.animeal.tabs.more.faq.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent.BackClicked
import com.epmedu.animeal.tabs.more.faq.presentation.FAQScreenEvent.CardClicked
import com.epmedu.animeal.tabs.more.faq.presentation.model.FrequentlyAskedQuestionCard
import com.epmedu.animeal.tabs.more.faq.presentation.ui.FAQHeader
import com.epmedu.animeal.tabs.more.faq.presentation.ui.toComposable
import com.epmedu.animeal.tabs.more.faq.presentation.viewmodel.FAQState
import com.epmedu.animeal.tabs.more.faq.util.generateLoremIpsum
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FAQScreenUI(
    state: FAQState,
    onEvent: (FAQScreenEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = remember {
            // Sticks last element to the bottom
            object : Arrangement.Vertical {
                override fun Density.arrange(
                    totalSize: Int,
                    sizes: IntArray,
                    outPositions: IntArray
                ) {
                    var currentOffset = 0
                    sizes.forEachIndexed { index, size ->
                        if (index == sizes.lastIndex) {
                            outPositions[index] = totalSize - size
                        } else {
                            outPositions[index] = currentOffset
                            currentOffset += size
                        }
                    }
                }
            }
        },
    ) {
        item { FAQHeader(onBack = { onEvent(BackClicked) }) }
        items(state.questionCards) {
            it.toComposable(
                onClick = { onEvent(CardClicked(it)) }
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
                questionCards = List(3) { index ->
                    FrequentlyAskedQuestionCard(
                        question = "Question ${index + 1}",
                        answer = generateLoremIpsum(),
                        isExpanded = index == 2
                    )
                }.toImmutableList()
            ),
            onEvent = {},
        )
    }
}
