package com.epmedu.animeal.tabs.more.donate

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.placeholder.ScreenPlaceholder
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun DonateScreenUI(
    onBack: () -> Unit,
) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.page_donate),
        onBack = onBack,
    )
}

@AnimealPreview
@Composable
private fun DonateScreenPreview() {
    AnimealTheme {
        DonateScreenUI(
            onBack = {},
        )
    }
}
