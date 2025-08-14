package com.epmedu.animeal.tabs.more.about.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.openWebsite
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun LegalLinks(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val links = stringArrayResource(R.array.about_links)

    Row(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        links.forEachIndexed { index, item ->
            val (_, title, url) = item.split("|")
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = when (index) {
                    0 -> Alignment.CenterStart
                    links.lastIndex -> Alignment.CenterEnd
                    else -> Alignment.Center
                }
            ) {
                Text(
                    text = title,
                    color = Color.Blue,
                    style = MaterialTheme.typography.body2.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .clickable {
                            context.openWebsite(url)
                        }
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun LegalLinksPreview() {
    AnimealTheme {
        LegalLinks(
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
