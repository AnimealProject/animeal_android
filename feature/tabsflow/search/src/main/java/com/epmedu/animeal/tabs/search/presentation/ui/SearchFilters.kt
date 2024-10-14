package com.epmedu.animeal.tabs.search.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

internal enum class SearchFilters(
    val stringRes: Int,
    val color: Color,
    val weight: Float
) {
    All(stringRes = R.string.filter_all, color = CustomColor.FilterGrey, weight = 0.29f),
    NewlyFed(
        stringRes = R.string.filter_newly_fed,
        color = CustomColor.FilterGreen,
        weight = 0.32f
    ),
    NoFood(stringRes = R.string.filter_no_food, color = CustomColor.FilterRed, weight = 0.38f)
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun SearchFilters(
    selectedFilter: SearchFilters,
    onSelectFilter: (SearchFilters) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SearchFilters.entries.forEach { filter ->
            Chip(
                onClick = { onSelectFilter(filter) },
                modifier = Modifier.weight(filter.weight),
                colors = ChipDefaults.chipColors(
                    backgroundColor = when (filter) {
                        selectedFilter -> filter.color
                        else -> filter.color.copy(alpha = 0.3f)
                    },
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = stringResource(filter.stringRes),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun SearchFiltersPreview() {
    AnimealTheme {
        Column {
            SearchFilters(
                selectedFilter = SearchFilters.All,
                onSelectFilter = {}
            )
            Divider()
            SearchFilters(
                selectedFilter = SearchFilters.NewlyFed,
                onSelectFilter = {}
            )
            Divider()
            SearchFilters(
                selectedFilter = SearchFilters.NoFood,
                onSelectFilter = {}
            )
        }
    }
}