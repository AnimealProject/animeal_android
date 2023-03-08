package com.epmedu.animeal.tabs.search.presentation.search

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchScreen() {
    val searchViewModel = hiltViewModel<SearchScreenViewModel>()
    val searchState by searchViewModel.stateFlow.collectAsState()

    val context = LocalContext.current.applicationContext

    SearchScreenUi(searchState, onEvent = {
        Toast.makeText(
            context, "${(it as? SearchScreenEvent.Search)?.query}", Toast.LENGTH_SHORT
        ).show()
//        viewModel.handleEvents(it)
    })
}