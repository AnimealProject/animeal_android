package com.epmedu.animeal.tabs.search.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.constants.Arguments
import com.epmedu.animeal.common.route.TabsRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.rememberAnimealBottomSheetState
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.search.presentation.ui.SearchScreenUi
import com.epmedu.animeal.tabs.search.presentation.viewmodel.SearchEvent
import com.epmedu.animeal.tabs.search.presentation.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen() {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchState by searchViewModel.stateFlow.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    val bottomSheetState = rememberAnimealBottomSheetState(AnimealBottomSheetValue.Hidden)

    SearchScreenUi(searchState, bottomSheetState, onEvent = {
        searchViewModel.handleEvents(it)
    })

    if (searchState.showingFeedingPoint != null) {
        LaunchedEffect(Unit) { bottomSheetState.expand() }
    }
    LaunchedEffect(Unit) {
        searchViewModel.events.collect {
            when(it) {
                is SearchEvent.ShowHomePage -> navigator.navigate(
                    TabsRoute.Home.withArg(
                        Arguments.FORCED_FEEDING_POINT_ID to it.feedingPointId,
                        Arguments.ANIMAL_TYPE to it.animalType.name
                    )
                )
            }
        }
    }
}