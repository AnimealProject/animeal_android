package com.epmedu.animeal.tabs.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointItem
import com.epmedu.animeal.foundation.listitem.ExpandableListItem
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.AnimealPagerTabRow
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.tabs.search.presentation.cats.CatsContent
import com.epmedu.animeal.tabs.search.presentation.dogs.DogsContent
import com.epmedu.animeal.tabs.search.presentation.model.GroupFeedingPointsModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
internal fun SearchScreenUi(searchState: SearchState) {
    Box(
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
                AnimalType.Dogs -> DogsContent(searchState.dogsState)
                AnimalType.Cats -> CatsContent()
            }
        }
    }
}

@Composable
fun AnimalExpandableList(
    padding: PaddingValues,
    groupedPoints: List<GroupFeedingPointsModel>,
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        modifier = Modifier.padding(top = 12.dp),
        contentPadding = PaddingValues(bottom = 60.dp)
    ) {

        items(groupedPoints) { group ->
            var isExpanded by remember(key1 = group.title) { mutableStateOf(group.isExpanded) }

            ExpandableListItem(
                title = group.title,
                onClick = {
                    group.isExpanded = !group.isExpanded
                    isExpanded = group.isExpanded
                },
                isExpanded = isExpanded
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    group.points.forEach { item ->
                        FeedingPointItem(
                            title = item.title,
                            status = item.feedStatus,
                            isFavourite = item.isFavourite,
                            onFavouriteChange = {
                            },
                            onClick = {
                            }
                        )
                    }
                }
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

@AnimealPreview
@Composable
private fun SearchScreenUiPreview() {
    AnimealTheme {
        SearchScreenUi(SearchState())
    }
}