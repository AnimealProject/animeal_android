package com.epmedu.animeal.favourites.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.favourites.presentation.ui.FavouritesScreenUI
import com.epmedu.animeal.foundation.dialog.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.dialog.bottomsheet.rememberAnimealBottomSheetState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouritesScreen() {
    val viewModel = hiltViewModel<FavouritesViewModel>()

    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(AnimealBottomSheetValue.Hidden)

    FavouritesScreenUI(state, bottomSheetState, onEvent = {
        viewModel.handleEvents(it)
    })

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is FavouritesScreenEvent.FeedSpotSelected -> {
                    launch {
                        if (bottomSheetState.isHidden) bottomSheetState.expand()
                    }
                }
                is FavouritesScreenEvent.FeedSpotChanged -> {
                    bottomSheetState.hide()
                }
                else -> {
                    // do nothing
                }
            }
        }
    }
}