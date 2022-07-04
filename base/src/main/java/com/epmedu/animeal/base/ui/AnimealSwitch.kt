package com.epmedu.animeal.base.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.epmedu.animeal.base.R
import com.epmedu.animeal.base.theme.AnimealTheme

/**
 * Shows the bar that holds 2 tabs.
 *
 * @param onTabSelected Called when the tab is switched.
 * @param modifier The [Modifier].
 */
@Composable
fun AnimealSwitch(
    modifier: Modifier = Modifier,
    onTabSelected: (tab: Tab) -> Unit
) {
    var currentTab by remember { mutableStateOf(Tab.Cats) }

    TabRow(
        selectedTabIndex = currentTab.ordinal,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier
            .size(width = 226.dp, height = 36.dp)
            .clip(RoundedCornerShape(10.dp)),
        indicator = { tabPositions ->
            TabIndicator(tabPositions, currentTab)
        },
        divider = {}
    ) {
        AnimalTab(
            tab = Tab.Dogs,
            selected = currentTab == Tab.Dogs,
            onClick = {
                onTabSelected(it)
                currentTab = it
            }
        )
        AnimalTab(
            tab = Tab.Cats,
            selected = currentTab == Tab.Cats,
            onClick = {
                onTabSelected(it)
                currentTab = it
            }
        )
    }
}

/**
 * Shows an indicator for the tab.
 *
 * @param tabPositions The list of [TabPosition]s from a [TabRow].
 * @param tab The [Tab] that is currently selected.
 */
@Composable
private fun TabIndicator(
    tabPositions: List<TabPosition>,
    tab: Tab
) {

    val transition = updateTransition(
        targetState = tab,
        label = "Tab indicator"
    )

    val indicatorLeft by transition.animateDp(
        label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }

    val indicatorRight by transition.animateDp(
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }

    Box(
        Modifier
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(width = indicatorRight - indicatorLeft)
            .fillMaxSize()
            .padding(all = 2.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(9.dp)
            )
            .zIndex(zIndex = -1f)
    )
}

/**
 * Shows a tab.
 *
 * @param tab current Tab type [Tab]
 * @param selected current tab selected state
 * @param onClick Called when this tab is clicked.
 * @param modifier The [Modifier].
 */
@Composable
private fun AnimalTab(
    modifier: Modifier = Modifier,
    tab: Tab,
    selected: Boolean,
    onClick: (Tab) -> Unit
) {
    Row(
        modifier = modifier
            .padding(all = 2.dp)
            .clip(shape = RoundedCornerShape(size = 9.dp))
            .clickable(onClick = { onClick(tab) }),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = tab.title),
            fontSize = 14.sp,
            modifier = Modifier.zIndex(1f),
            color = if (selected) Color.White else MaterialTheme.colors.onSurface
        )
    }
}

enum class Tab(@StringRes val title: Int) {
    Dogs(R.string.tab_dogs_title),
    Cats(R.string.tab_cats_title)
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AnimealSwitchPreview() {
    AnimealTheme {
        AnimealSwitch(onTabSelected = { })
    }
}