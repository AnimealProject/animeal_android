package com.epmedu.animeal.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.R

@Composable
internal fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.home), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
internal fun FavoritesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.favorites), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
internal fun AnalyticsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.analytics), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
internal fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.search), modifier = Modifier.align(Alignment.Center))
    }
}