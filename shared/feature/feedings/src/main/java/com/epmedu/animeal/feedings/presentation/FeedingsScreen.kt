package com.epmedu.animeal.feedings.presentation

import androidx.compose.runtime.Composable
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN

@Composable
fun FeedingsScreen() {
    BottomBarVisibility(HIDDEN)

    FeedingsScreenUi()
}
