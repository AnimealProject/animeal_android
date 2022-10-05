package com.epmedu.animeal.tabs.more

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun MoreScreen() {
    val navigator = LocalNavigator.currentOrThrow

    BottomBarVisibility(SHOWN)

    MoreScreenUi(
        onNavigate = navigator::navigate,
    )
}