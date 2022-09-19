package com.epmedu.animeal.favourites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavouritesScreen() {
    val viewModel = hiltViewModel<FavouritesViewModel>()
    val state by viewModel.stateFlow.collectAsState()

    FavouritesScreenUI(state) { viewModel.handleEvents(it) }
}