package com.epmedu.animeal.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.favourites.data.model.FavouriteFeedingPoint
import com.epmedu.animeal.favourites.ui.FavouriteFeedingPointItem
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun FavouritesScreenUI(
    state: FavouritesState,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .bottomBarPadding()
            .background(color = MaterialTheme.colors.secondaryVariant),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.favourites),
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { padding ->
        when {
            state.favourites.isEmpty() -> {
                EmptyState(padding)
            }
            else -> {
                FavouritesList(padding, state.favourites, onEvent)
            }
        }
    }
}

@Composable
private fun EmptyState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.favourites_no_items),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun FavouritesList(
    padding: PaddingValues,
    favourites: List<FavouriteFeedingPoint>,
    onEvent: (FavouritesScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 12.dp, bottom = 32.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(30.dp)

        ) {
            items(favourites) { item ->
                FavouriteFeedingPointItem(
                    title = item.title,
                    status = item.status,
                    isFavourite = item.isFavourite,
                    onFavouriteChange = { onEvent(FavouritesScreenEvent.FeedSpotRemove(item.id)) },
                    onClick = { onEvent(FavouritesScreenEvent.FeedSpotSelected(item.id)) }
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun FavouritesScreenPreview() {
    val title = "FeedSpot"
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                listOf(
                    FavouriteFeedingPoint(title = title, isFavourite = true),
                    FavouriteFeedingPoint(title = title, isFavourite = true),
                    FavouriteFeedingPoint(title = title, isFavourite = true)
                )
            )
        ) {}
    }
}

@AnimealPreview
@Composable
private fun FavouritesScreenEmptyPreview() {
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                emptyList()
            )
        ) {}
    }
}