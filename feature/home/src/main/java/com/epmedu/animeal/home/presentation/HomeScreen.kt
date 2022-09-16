package com.epmedu.animeal.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val state by homeViewModel.stateFlow.collectAsState()

    HomeScreenUI(state = state)
}
