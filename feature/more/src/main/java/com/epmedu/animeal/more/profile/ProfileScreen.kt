package com.epmedu.animeal.more.profile

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun ProfileScreen() {
    val viewModel: ProfileViewModel = viewModel()
    val navigator = LocalNavigator.currentOrThrow

    ProfileScreenUI(
        onBack = navigator::popBackStack,
        onEdit = viewModel::edit
    )
}
