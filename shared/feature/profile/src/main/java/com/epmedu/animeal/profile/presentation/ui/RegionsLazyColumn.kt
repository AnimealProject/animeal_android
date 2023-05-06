@file:OptIn(ExperimentalMaterialApi::class)

package com.epmedu.animeal.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.emoji2.text.EmojiCompat
import androidx.emoji2.text.EmojiCompat.LOAD_STATE_SUCCEEDED
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.model.countryName
import com.epmedu.animeal.profile.domain.model.flagEmoji
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegionsLazyColumn(
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    onRegionClick: (Region) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        items(
            items = Region.values().apply {
                sortBy { region -> region.countryName() }
            }
        ) { region ->
            ListItem(
                modifier = Modifier.clickable {
                    scope.launch { bottomSheetState.hide() }
                    onRegionClick(region)
                },
                text = {
                    Text(
                        region.codesListText()
                    )
                },
            )
        }
    }
}

@AnimealPreview
@Composable
fun RegionsLazyColumnPreview() {
    AnimealTheme {
        RegionsLazyColumn(
            scope = rememberCoroutineScope(),
            bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
            onRegionClick = {}
        )
    }
}

private fun Region.codesListText(): String {
    val emojiCompat = EmojiCompat.get()
    val flag = when (emojiCompat.loadState) {
        LOAD_STATE_SUCCEEDED -> emojiCompat.process(flagEmoji())
        else -> flagEmoji()
    }
    return "$flag $phoneNumberCode ${countryName()}"
}