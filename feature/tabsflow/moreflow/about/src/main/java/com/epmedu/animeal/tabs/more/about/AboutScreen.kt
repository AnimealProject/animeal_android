package com.epmedu.animeal.tabs.more.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.epmedu.animeal.extensions.*
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.foundation.util.generateLoremIpsum
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun AboutScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current

    BottomBarVisibility(HIDDEN)

    AboutScreenUI(
        onBack = navigator::popBackStack,
        onSocialFacebookClick = {
            context.openFacebook()
        },
        onSocialInstagramClick = {
            context.openInstagram()
        },
        onSocialLinkedinClick = {
            context.openLinkedin()
        },
        onSocialWebClick = {
            context.openAnimealWebsite()
        },
        text = generateLoremIpsum(),
        text2 = generateLoremIpsum()
    )
}
