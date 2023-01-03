package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.tabs.more.faq.presentation.model.FrequentlyAskedQuestionCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun FrequentlyAskedQuestionCard.toComposable(
    onClick: () -> Unit
) {
    Column {
        ListItem(
            modifier = Modifier
                .padding(top = 16.dp) // not clickable padding
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp), // clickable padding
            text = {
                Text(text = question)
            },
            trailing = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = when {
                        isExpanded -> Icons.Default.KeyboardArrowUp
                        else -> Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = question
                )
            }
        )
        AnimatedVisibility(visible = isExpanded) {
            Card(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 3.dp
            ) {
                Text(
                    text = answer,
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(start = 4.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun FrequentlyAskedQuestionCardPreview(@PreviewParameter(LoremIpsum::class) text: String) {
    AnimealTheme {
        Column {
            FrequentlyAskedQuestionCard(
                question = "Expanded",
                answer = text,
                isExpanded = true
            ).toComposable(
                onClick = {}
            )
            Divider()
            FrequentlyAskedQuestionCard(
                question = "Collapsed",
                answer = text
            ).toComposable(
                onClick = {}
            )
        }
    }
}