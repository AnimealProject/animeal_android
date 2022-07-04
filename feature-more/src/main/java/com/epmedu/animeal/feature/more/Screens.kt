package com.epmedu.animeal.feature.more

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R

@Composable
internal fun ProfilePageScreen() {
    val navigator = LocalNavigator.currentOrThrow

    ScreenPlaceholder(
        title = stringResource(id = R.string.page_profile),
        onBack = { navigator.popBackStack() }
    )
}

@Composable
internal fun DonateScreen() {
    val navigator = LocalNavigator.currentOrThrow

    ScreenPlaceholder(
        title = stringResource(id = R.string.page_donate),
        onBack = { navigator.popBackStack() }
    )
}

@Composable
internal fun HelpScreen() {
    val navigator = LocalNavigator.currentOrThrow

    ScreenPlaceholder(
        title = stringResource(id = R.string.page_help),
        onBack = { navigator.popBackStack() }
    )
}

@Composable
internal fun AboutScreen() {
    val navigator = LocalNavigator.currentOrThrow

    ScreenPlaceholder(
        title = stringResource(id = R.string.page_about),
        onBack = { navigator.popBackStack() }
    )
}

@Composable
private fun ScreenPlaceholder(title: String, onBack: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(title = title) {
                BackButton(onBack)
            }
        },
        content = {}
    )
}
