package com.epmedu.animeal.tabs.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.switch.AnimealPagerTabRow
import com.epmedu.animeal.foundation.switch.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
internal fun SearchScreenUi() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
    ) {
        AnimalTypeHorizontalPager(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 30.dp,
                end = 30.dp
            )
        ) { animalType ->
            when (animalType) {
                AnimalType.Dogs -> Box(
                    modifier = Modifier
                        .background(color = Color.Red)
                        .fillMaxSize()
                ) {
                    Text("Dogs")
                }
                AnimalType.Cats -> Text("Cats")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimalTypeHorizontalPager(
    modifier: Modifier = Modifier,
    content: @Composable (animalType: AnimalType) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val pages = remember { AnimalType.values() }
    val pagerState = rememberPagerState()

    Column() {
        AnimealPagerTabRow(
            modifier = modifier,
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

@AnimealPreview
@Composable
private fun SearchScreenUiPreview() {
    AnimealTheme {
        SearchScreenUi()
    }
}