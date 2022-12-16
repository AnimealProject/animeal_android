package com.epmedu.animeal.tabs.more.faq

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.placeholder.ScreenPlaceholder
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.faq.viewmodel.FAQState

@Composable
internal fun FAQScreenUI(
    state: FAQState,
    onBack: () -> Unit,
) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.page_faq),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp),
                    text = stringResource(
                        id = R.string.faq_page_app_version,
                        state.appVersionName
                    ),
                    style = MaterialTheme.typography.caption
                )
            }
        },
        onBack = onBack,
    )
}

@AnimealPreview
@Composable
private fun FAQScreenUIPreview() {
    AnimealTheme {
        FAQScreenUI(
            state = FAQState(),
            onBack = {},
        )
    }
}
