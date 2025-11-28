package com.epmedu.animeal.tabs.more.about

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.text.AnimealUnorderedList
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.about.ui.AboutFooter
import com.epmedu.animeal.tabs.more.about.ui.AboutHeading
import com.epmedu.animeal.tabs.more.about.ui.AboutText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun AboutScreenUI(
    currentVersion: String,
    onBack: () -> Unit,
    onLinkClick: (type: LinkMediaType, url: String?) -> Unit,
) {
    val paragraphsArray = stringArrayResource(R.array.about_paragraphs)
    val horizontalPadding = 26.dp
    Column {
        AboutHeading(onBack, horizontalPadding)

        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 136.dp),
                verticalArrangement = Arrangement.Top
            ) {
                items(paragraphsArray) { paragraph ->
                    AboutText(
                        text = paragraph,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .padding(horizontal = horizontalPadding)
                    )
                }
                item {
                    AnimealUnorderedList(
                        items = stringArrayResource(R.array.about_areas_of_work).toList(),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                        drawItem = { text ->
                            AboutText(text = text)
                        }
                    )
                    HeightSpacer(height = 16.dp)
                }
            }
            AboutFooter(
                currentVersion = currentVersion,
                onLinkClick = { onLinkClick(it, null) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = horizontalPadding)
            )
        }
    }
}

@AnimealPreview
@Composable
private fun AboutScreenUIPreview() {
    AnimealTheme {
        AboutScreenUI(
            currentVersion = "0.000",
            onBack = {},
            onLinkClick = { _, _ -> }
        )
    }
}
