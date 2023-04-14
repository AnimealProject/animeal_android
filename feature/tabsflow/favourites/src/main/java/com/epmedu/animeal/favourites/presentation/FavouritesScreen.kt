package com.epmedu.animeal.favourites.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.constants.Arguments.ANIMAL_TYPE
import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.route.TabsRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.favourites.presentation.ui.FavouritesScreenUI
import com.epmedu.animeal.favourites.presentation.viewmodel.FavouritesEvent
import com.epmedu.animeal.favourites.presentation.viewmodel.FavouritesViewModel
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouritesScreen() {
    val viewModel = hiltViewModel<FavouritesViewModel>()
    val navigator = LocalNavigator.currentOrThrow

    val state by viewModel.stateFlow.collectAsState()
    val bottomSheetState = rememberAnimealBottomSheetState(
        initialValue = AnimealBottomSheetValue.Hidden,
    )

    FavouritesScreenUI(state, bottomSheetState, onEvent = {
        viewModel.handleEvents(it)
    })
    if (state.showingFeedingPoint != null) {
        LaunchedEffect(Unit) { bottomSheetState.expand() }
    }
    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is FavouritesEvent.ShowHomePage -> navigator.navigate(
                    TabsRoute.Home.withArg(
                        FORCED_FEEDING_POINT_ID to it.feedingPointId,
                        ANIMAL_TYPE to it.animalType.name
                    )
                )
            }
        }
    }
}