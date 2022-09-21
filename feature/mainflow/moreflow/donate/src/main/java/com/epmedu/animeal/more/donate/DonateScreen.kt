package com.epmedu.animeal.more.donate

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun DonateScreen() {
    val navigator = LocalNavigator.currentOrThrow

    BottomBarVisibility(HIDDEN)

    DonateScreenUI(
        onBack = navigator::popBackStack,
    )
}
