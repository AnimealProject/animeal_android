package com.epmedu.animeal.more.root

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun MoreScreen() {
    val navigator = LocalNavigator.currentOrThrow

    BottomBarVisibility(SHOWN)

    MoreScreenUi(
        onNavigate = navigator::navigate,
    )
}
