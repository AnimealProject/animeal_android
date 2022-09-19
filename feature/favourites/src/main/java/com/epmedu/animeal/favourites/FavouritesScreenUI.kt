package com.epmedu.animeal.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.favourites.data.model.FavouriteFeedSpot
import com.epmedu.animeal.favourites.ui.FavouriteFeedSpotItem
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun FavouritesScreenUI(state: FavouritesState) {
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
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 12.dp, bottom = 32.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(30.dp)

            ) {
                items(state.favourites) { item ->
                    FavouriteFeedSpotItem(
                        title = item.title,
                        status = item.status,
                        isFavourite = item.isFavourite,
                        onFavouriteChange = {},
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MoreScreenPreview() {
    val title = "FeedSpot"
    AnimealTheme {
        FavouritesScreenUI(
            FavouritesState(
                listOf(
                    FavouriteFeedSpot(title = title, isFavourite = true),
                    FavouriteFeedSpot(title = title, isFavourite = true),
                    FavouriteFeedSpot(title = title, isFavourite = true)
                )
            )
        )
    }
}