package com.epmedu.animeal.more.help

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun HelpScreen() {
    val navigator = LocalNavigator.currentOrThrow

    HelpScreenUI(
        onBack = navigator::popBackStack,
    )
}
