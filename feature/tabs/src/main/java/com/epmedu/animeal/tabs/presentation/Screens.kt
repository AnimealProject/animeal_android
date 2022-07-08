package com.epmedu.animeal.tabs.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.resources.R

@Composable
internal fun FavoritesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.tab_favorites), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
internal fun AnalyticsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.tab_analytics), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
internal fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.tab_search), modifier = Modifier.align(Alignment.Center))
    }
}