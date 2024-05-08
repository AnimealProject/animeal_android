package com.epmedu.animeal.foundation.tabs

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme

/**
 * Shows the bar that holds 2 tabs.
 *
 * @param defaultAnimalType default animal type.
 * @param onSelectTab Called when the tab is switched.
 * @param modifier The [Modifier].
 */
@Composable
fun AnimealSwitch(
    defaultAnimalType: AnimalType,
    onSelectTab: (animalType: AnimalType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentAnimalType by remember(defaultAnimalType) { mutableStateOf(defaultAnimalType) }

    TabRow(
        selectedTabIndex = currentAnimalType.ordinal,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier
            .size(width = 226.dp, height = 36.dp)
            .clip(RoundedCornerShape(10.dp)),
        indicator = { tabPositions ->
            TabIndicator(tabPositions, currentAnimalType)
        },
        divider = {}
    ) {
        AnimealSwitchTab(
            titleResId = AnimalType.Dogs.title,
            selected = currentAnimalType == AnimalType.Dogs,
            onClick = {
                onSelectTab(AnimalType.Dogs)
                currentAnimalType = AnimalType.Dogs
            }
        )
        AnimealSwitchTab(
            titleResId = AnimalType.Cats.title,
            selected = currentAnimalType == AnimalType.Cats,
            onClick = {
                onSelectTab(AnimalType.Cats)
                currentAnimalType = AnimalType.Cats
            }
        )
    }
}

@AnimealPreview
@Composable
private fun AnimealSwitchPreview() {
    AnimealTheme {
        AnimealSwitch(onSelectTab = { }, defaultAnimalType = AnimalType.Cats)
    }
}