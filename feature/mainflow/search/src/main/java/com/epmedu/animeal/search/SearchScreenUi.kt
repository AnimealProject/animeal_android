package com.epmedu.animeal.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun SearchScreenUi() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.tab_search),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@AnimealPreview
@Composable
private fun SearchScreenUiPreview() {
    AnimealTheme {
        SearchScreenUi()
    }
}