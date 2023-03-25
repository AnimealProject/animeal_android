package com.epmedu.animeal.tabs.search.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.tabs.AnimealPagerTabRow
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimalTypeHorizontalPager(
    tabRowHorizontalPadding: Dp = 0.dp,
    scope: CoroutineScope,
    content: @Composable (animalType: AnimalType) -> Unit,
) {
    val pages = remember { AnimalType.values() }
    val pagerState = rememberPagerState()

    Column {
        AnimealPagerTabRow(
            modifier = Modifier.padding(horizontal = tabRowHorizontalPadding),
            pagerState = pagerState,
            onSelectTab = { tabIndex ->
                scope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            }
        )

        HorizontalPager(
            count = pages.size,
            state = pagerState,
            content = { page ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    content(pages[page])
                }
            }
        )
    }
}