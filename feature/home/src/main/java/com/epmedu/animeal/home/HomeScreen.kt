package com.epmedu.animeal.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val state by homeViewModel.stateFlow.collectAsState()

    HomeScreenUI(state = state)
}
