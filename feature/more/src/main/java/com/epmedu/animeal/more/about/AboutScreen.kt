package com.epmedu.animeal.more.about

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun AboutScreen() {
    val navigator = LocalNavigator.currentOrThrow

    AboutScreenUI(
        onBack = navigator::popBackStack,
    )
}
