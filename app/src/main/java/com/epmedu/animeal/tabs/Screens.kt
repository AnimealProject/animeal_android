package com.epmedu.animeal.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.R
import com.epmedu.animeal.base.ui.AnimealSwitch

@Composable
internal fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimealSwitch(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .padding(top = 24.dp),
            onTabSelected = {})
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