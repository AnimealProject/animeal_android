package com.epmedu.animeal.feature.more

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.epmedu.animeal.feature_more.R

@Composable
internal fun ProfilePageScreen(navController: NavController) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.profile_page),
        onBack =  { navController.popBackStack() }
    )
}

@Composable
internal fun DonateScreen(navController: NavController) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.donate),
        onBack =  { navController.popBackStack() }
    )
}

@Composable
internal fun HelpScreen(navController: NavController) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.help),
        onBack =  { navController.popBackStack() }
    )
}

@Composable
internal fun AboutScreen(navController: NavController) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.about),
        onBack =  { navController.popBackStack() }
    )
}

@Composable
private fun ScreenPlaceholder(title: String, onBack: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = title,
                onBack = { onBack() }
            )
        },
        content = {}
    )
}