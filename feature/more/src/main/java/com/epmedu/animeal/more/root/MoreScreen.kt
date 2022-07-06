package com.epmedu.animeal.more.root

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.more.MoreViewModel
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun MoreScreen() {
    val viewModel: MoreViewModel = viewModel()
    val navigator = LocalNavigator.currentOrThrow

    MoreScreenUi(
        onLogout = viewModel::logout,
        onNavigate = navigator::navigate,
    )
}
