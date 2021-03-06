package com.epmedu.animeal.more.donate

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun DonateScreen() {
    val navigator = LocalNavigator.currentOrThrow

    DonateScreenUI(
        onBack = navigator::popBackStack,
    )
}
