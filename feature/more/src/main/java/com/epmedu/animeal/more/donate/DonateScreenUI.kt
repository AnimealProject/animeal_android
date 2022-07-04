package com.epmedu.animeal.more.donate

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.more.ui.common.ScreenPlaceholder
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

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun DonateScreenPreview() {
    AnimealTheme {
        DonateScreenUI(
            onBack = {},
        )
    }
}
