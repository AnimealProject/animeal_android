package com.epmedu.animeal.foundation.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset

/**
 * Shows the tab bar that can hold several tabs.
 *
 *  - default [backgroundColor] is [Color.Transparent]
 *
 * @param onSelectTab Called when the tab is switched.
 * @param backgroundColor background color.
 * @param modifier The [Modifier].
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimealPagerTabRow(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    pagerState: PagerState,
    onSelectTab: (index: Int) -> Unit
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        indicator = { tabPositions ->
            AnimealPagerTabIndicator(
                Modifier.pagerTabIndicatorOffset(
                    pagerState,
                    tabPositions
                )
            )
        },
        divider = { AnimealPagerTabDivider() },
        backgroundColor = backgroundColor
    ) {
        AnimalType.values().forEachIndexed { index, animalType ->
            AnimealPagerTab(
                animalType = animalType,
                selected = pagerState.currentPage == index,
                onClick = { onSelectTab(index) })
        }
    }
}

@Composable
private fun AnimealPagerTab(
    modifier: Modifier = Modifier,
    animalType: AnimalType,
    selected: Boolean,
    onClick: () -> Unit
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier
            .height(50.dp)
    ) {
        Box(
            modifier = modifier
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

@Composable
private fun AnimealPagerTabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary
) {
    Spacer(
        modifier
            .padding(start = 30.dp, end = 30.dp)
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

@OptIn(ExperimentalPagerApi::class)
@AnimealPreview
@Composable
private fun AnimealTabPreview() {
    AnimealTheme {
        AnimealPagerTabRow(pagerState = PagerState(), onSelectTab = {})
    }
}