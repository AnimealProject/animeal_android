package com.epmedu.animeal.more.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.more.R
import com.epmedu.animeal.more.ui.common.ScreenPlaceholder

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