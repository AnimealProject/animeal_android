package com.epmedu.animeal.feature.more.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.feature.more.ScreenPlaceholder
import com.epmedu.animeal.feature_more.R

@Composable
internal fun AboutScreen(navController: NavController) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.about),
        onBack = { navController.popBackStack() }
    )
}

@Preview
@Composable
fun AboutScreenPreview() {
    AnimealTheme {
        AboutScreen(navController = NavController(LocalContext.current))
    }
}