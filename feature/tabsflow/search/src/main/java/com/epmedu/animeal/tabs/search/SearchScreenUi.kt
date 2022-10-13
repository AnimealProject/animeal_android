package com.epmedu.animeal.tabs.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun SearchScreenUi() {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = stringResource(R.string.tab_search),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@AnimealPreview
@Composable
private fun SearchScreenUiPreview() {
    AnimealTheme {
        SearchScreenUi()
    }
}