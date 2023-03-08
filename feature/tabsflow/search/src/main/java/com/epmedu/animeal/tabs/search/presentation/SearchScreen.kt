package com.epmedu.animeal.tabs.search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchScreen() {
    val searchViewModel = hiltViewModel<SearchScreenViewModel>()
    val searchState by searchViewModel.stateFlow.collectAsState()

    SearchScreenUi(searchState)
}