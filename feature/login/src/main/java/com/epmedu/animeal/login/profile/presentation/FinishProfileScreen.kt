package com.epmedu.animeal.login.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.login.presentation.navigateToTabs
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun FinishProfileScreen(isFirstTime: Boolean = true) {
    val viewModel: FinishProfileViewModel = hiltViewModel()
    val navigator = LocalNavigator.currentOrThrow

    FinishProfileScreenUi(
        viewModel = viewModel,
        onBack = navigator::popBackStack
    )

    LaunchedEffect(Unit) {
        viewModel.validationEvents.collect {
            when (it) {
                FinishProfileViewModel.ValidationEvent.Success -> {
                    if (isFirstTime) {
                        navigator.navigateToTabs()
                    } else {
                        navigator.popBackStack()
                    }
                }
            }
        }
    }
}