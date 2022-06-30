package com.epmedu.animeal.feature.more.donate

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.feature.more.ScreenPlaceholder
import com.epmedu.animeal.feature_more.R

@Composable
internal fun DonateScreen(navController: NavController) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.donate),
        onBack = { navController.popBackStack() }
    )
}

@Preview
@Composable
fun DonateScreenPreview() {
    AnimealTheme {
        DonateScreen(navController = NavController(LocalContext.current))
    }
}