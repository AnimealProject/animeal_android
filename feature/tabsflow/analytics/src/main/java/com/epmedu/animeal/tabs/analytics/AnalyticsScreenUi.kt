package com.epmedu.animeal.tabs.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.common.AnimealPopUpScreen
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun AnalyticsScreenUi() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        TopBar(
            title = stringResource(id = R.string.feeding_analysis)
        )
        AnimealPopUpScreen(
            painterResource = painterResource(id = R.drawable.ic_analysis_soon),
            titleText = R.string.coming_soon,
            subtitleText = R.string.thank_you_for_visiting,
            modifier = Modifier
                .fillMaxSize()
                .bottomBarPadding()
        )
    }
}

@AnimealPreview
@Composable
private fun AnalyticsScreenUiPreview() {
    AnimealTheme {
        AnalyticsScreenUi()
    }
}