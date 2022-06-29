package com.epmedu.animeal.tabs

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.R

@Composable
internal fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Text(text = stringResource(R.string.home), modifier = Modifier.align(Alignment.Center))
        var number by remember { mutableStateOf("") }

        InputFieldView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = "Name",
            placeholder = "Enter your name",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                number = it
                Log.wtf("Taken Value from input", "$it")
            },
            number
        )
    }
}

@Composable
internal fun MoreScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.more), modifier = Modifier.align(Alignment.Center))
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