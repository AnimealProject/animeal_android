package com.epmedu.animeal.foundation.switch

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.switch.model.AnimalType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimalTypeHorizontalPager(
    modifier: Modifier = Modifier,
    content: @Composable (animalType: AnimalType) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val pages = remember { AnimalType.values() }
    val pagerState = rememberPagerState()

    Column(modifier = modifier) {
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
            content = { page -> content(pages[page]) }
        )
    }
}