package com.epmedu.animeal.more.help

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
internal fun HelpScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val helpViewModel = hiltViewModel<HelpViewModel>()

    val state by helpViewModel.stateFlow.collectAsState()

    HelpScreenUI(
        helpState = state,
        onBack = navigator::popBackStack,
    )
}
