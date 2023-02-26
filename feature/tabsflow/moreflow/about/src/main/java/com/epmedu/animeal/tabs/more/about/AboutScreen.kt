package com.epmedu.animeal.tabs.more.about

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.openAnimealWebsite
import com.epmedu.animeal.extensions.openFacebook
import com.epmedu.animeal.extensions.openInstagram
import com.epmedu.animeal.extensions.openLinkedin
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun AboutScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current

    BottomBarVisibility(HIDDEN)

    AboutScreenUI(
        onBack = navigator::popBackStack,
        onSocialClick = { socialMedia -> handleSocialClick(context, socialMedia) }
    )
}

fun handleSocialClick(context: Context, type: SocialMedia) {
    when (type) {
        SocialMedia.FACEBOOK -> context.openFacebook()
        SocialMedia.INSTAGRAM -> context.openInstagram()
        SocialMedia.LINKEDIN -> context.openLinkedin()
        SocialMedia.WEB -> context.openAnimealWebsite()
    }
}

enum class SocialMedia {
    FACEBOOK,
    INSTAGRAM,
    LINKEDIN,
    WEB
}