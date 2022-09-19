package com.epmedu.animeal.favourites

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavouritesScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    FavouritesScreenUI(viewModel.state)
}