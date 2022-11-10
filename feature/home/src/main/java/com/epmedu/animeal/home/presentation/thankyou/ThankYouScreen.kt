package com.epmedu.animeal.home.presentation.thankyou

import androidx.compose.runtime.Composable
import com.epmedu.animeal.common.route.MainRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun ThankYouScreen() {
    val navigator = LocalNavigator.currentOrThrow

    ThankYouScreenUI(
        onBackToHome = {
            navigator.navigate(MainRoute.Tabs.name)
        }
    )
}