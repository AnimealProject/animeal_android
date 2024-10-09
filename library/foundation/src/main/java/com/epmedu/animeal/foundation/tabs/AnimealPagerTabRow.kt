package com.epmedu.animeal.foundation.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalRippleConfiguration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme

/**
 * Shows the tab bar that can hold several tabs.
 *
 *  - default [backgroundColor] is [Color.Transparent]
 *
 * @param pagerState state of the pager.
 * @param onSwitchTab Called when the tab is switched.
 * @param modifier The [Modifier].
 * @param backgroundColor background color.
 */
@Composable
fun AnimealPagerTabRow(
    pagerState: PagerState,
    onSwitchTab: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        indicator = { tabPositions ->
            AnimealPagerTabIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
            )
        },
        divider = { AnimealPagerTabDivider() },
        backgroundColor = backgroundColor
    ) {
        AnimalType.entries.forEachIndexed { index, animalType ->

            AnimealPagerTab(
                animalType = animalType,
                selected = pagerState.currentPage == index,
                onClick = { onSwitchTab(index) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimealPagerTab(
    animalType: AnimalType,
    selected: Boolean,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Tab(
            selected = selected,
            onClick = onClick,
            modifier = Modifier
                .height(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 4.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = stringResource(id = animalType.title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .zIndex(1f)
                        .padding(horizontal = 6.dp),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun AnimealPagerTabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary
) {
    Spacer(
        modifier
            .height(2.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(size = 16.dp)
            )
    )
}

@Composable
private fun AnimealPagerTabDivider(modifier: Modifier = Modifier) {
    TabRowDefaults.Divider(
        modifier = modifier.padding(bottom = 0.5.dp),
        thickness = 1.dp
    )
}

@AnimealPreview
@Composable
private fun AnimealTabPreview() {
    AnimealTheme {
        AnimealPagerTabRow(pagerState = rememberPagerState { 2 }, onSwitchTab = {})
    }
}