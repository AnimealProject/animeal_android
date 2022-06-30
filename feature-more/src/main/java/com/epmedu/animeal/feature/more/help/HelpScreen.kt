package com.epmedu.animeal.feature.more.help

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.feature.more.ScreenPlaceholder
import com.epmedu.animeal.feature_more.R

@Composable
internal fun HelpScreen(navController: NavController) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.help),
        onBack = { navController.popBackStack() }
    )
}

@Preview
@Composable
fun HelpScreenPreview() {
    AnimealTheme {
        HelpScreen(navController = NavController(LocalContext.current))
    }
}