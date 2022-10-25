package com.epmedu.animeal.feedconfirmation.presentation

import androidx.compose.runtime.Composable
import com.epmedu.animeal.common.route.TabsRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun FeedConfirmationScreen() {
    val navigator = LocalNavigator.currentOrThrow

    FeedConfirmationUI(
        onAgreeClick = { navigator.navigate(TabsRoute.Home.name) },
        onCancelClick = { navigator.popBackStack() }
    )
}