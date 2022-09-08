package com.epmedu.animeal.more.account

import androidx.compose.runtime.Composable
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun AccountScreen() {
    val navigator = LocalNavigator.currentOrThrow

    AccountScreenUI(
        onBack = navigator::popBackStack
    )
}
