package com.epmedu.animeal.tabs.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.switch.AnimealPagerTabRow
import com.epmedu.animeal.foundation.switch.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
internal fun SearchScreenUi() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondaryVariant),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.tab_search),
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { padding ->
        ScreenContent(padding)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScreenContent(padding: PaddingValues) {
    val scope = rememberCoroutineScope()

    val pages = remember { AnimalType.values() }
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.padding(padding)) {
        AnimealPagerTabRow(
            pagerState = pagerState,
            onSelectTab = { tabIndex ->
                scope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            })

        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = AnimalType.values()[page].title),
                )
            }
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