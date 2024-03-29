package com.epmedu.animeal.tabs.more.faq.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.more.faq.presentation.viewmodel.FAQViewModel

@Composable
fun FAQScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel = hiltViewModel<FAQViewModel>()

    val state by viewModel.stateFlow.collectAsState()

    BottomBarVisibility(HIDDEN)

    FAQScreenUI(
        state = state,
        onBack = navigator::popBackStack,
        onEvent = viewModel::handleEvents
    )
}
