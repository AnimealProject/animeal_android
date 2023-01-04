package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.listitem.ExpandableListItem
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import com.epmedu.animeal.tabs.more.faq.util.generateLoremIpsum

@Composable
internal fun FAQListItem(
    frequentlyAskedQuestion: FrequentlyAskedQuestion,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    ExpandableListItem(
        title = frequentlyAskedQuestion.question,
        onClick = onClick,
        modifier = Modifier.padding(top = 16.dp),
        isExpanded = isExpanded
    ) {
        Card(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = 3.dp
        ) {
            Text(
                text = frequentlyAskedQuestion.answer,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(start = 4.dp),
                fontSize = 14.sp
            )
        }
    }
}

@AnimealPreview
@Composable
private fun FAQCardPreview() {
    val answer = generateLoremIpsum()

    AnimealTheme {
        Column {
            FAQListItem(
                frequentlyAskedQuestion = FrequentlyAskedQuestion(
                    question = "Expanded",
                    answer = answer
                ),
                isExpanded = true,
                onClick = {}
            )
            Divider()
            FAQListItem(
                frequentlyAskedQuestion = FrequentlyAskedQuestion(
                    question = "Collapsed",
                    answer = answer
                ),
                isExpanded = false,
                onClick = {}
            )
        }
    }
}