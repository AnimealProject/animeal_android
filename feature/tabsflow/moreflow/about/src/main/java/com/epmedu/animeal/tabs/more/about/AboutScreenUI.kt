package com.epmedu.animeal.tabs.more.about

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.layout.LastElementBottom
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
    onSocialClick: (type: SocialMedia) -> Unit,
) {
    val paragraphsArray = stringArrayResource(R.array.about_paragraphs)
    val horizontalPadding = 36.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.LastElementBottom
    ) {
        item {
            AboutHeading(onBack, horizontalPadding)
            HeightSpacer(height = 5.dp)
        }
        items(paragraphsArray) { paragraph ->
            AboutText(
                text = paragraph,
                modifier = Modifier
                    .padding(top = 25.dp)
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
        }
        item {
            AboutFooter(
                currentVersion = currentVersion,
                onSocialClick = onSocialClick,
                modifier = Modifier
                    .padding(top = 35.dp)
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
            onSocialClick = {}
        )
    }
}
