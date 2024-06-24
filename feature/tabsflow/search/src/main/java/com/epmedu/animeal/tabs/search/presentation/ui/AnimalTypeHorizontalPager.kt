package com.epmedu.animeal.tabs.search.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.tabs.AnimealPagerTabRow
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.AnimalTypeChange
import com.epmedu.animeal.tabs.search.presentation.viewmodel.SearchState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalTypeHorizontalPager(
    state: SearchState,
    tabRowHorizontalPadding: Dp = 0.dp,
    scope: CoroutineScope,
    onEvent: (SearchScreenEvent) -> Unit,
    content: @Composable (animalType: AnimalType) -> Unit,
) {
    val pages = remember { AnimalType.values() }
    val pagerState = rememberPagerState()

    Column {
        AnimealPagerTabRow(
            modifier = Modifier.padding(horizontal = tabRowHorizontalPadding),
            pagerState = pagerState,
            onSwitchTab = { tabIndex ->
                scope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            }
        )

        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                content(pages[page])
            }
        }
    }

    LaunchedEffect(state.animalType) {
        pagerState.animateScrollToPage(state.animalType.ordinal)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onEvent(AnimalTypeChange(pages[page]))
        }
    }
}