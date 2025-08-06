package com.epmedu.animeal.tabs.more.about

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.openAnimealWebsite
import com.epmedu.animeal.extensions.openFacebook
import com.epmedu.animeal.extensions.openInstagram
import com.epmedu.animeal.extensions.openLinkedin
import com.epmedu.animeal.extensions.openWebsite
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.tabs.more.about.viewmodel.AboutScreenViewModel

@Composable
fun AboutScreen() {
    val viewModel = hiltViewModel<AboutScreenViewModel>()
    val state by viewModel.stateFlow.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current

    BottomBarVisibility(HIDDEN)

    AboutScreenUI(
        currentVersion = state.currentVersion,
        onBack = navigator::popBackStack,
        onLinkClick = { linkMediaType, url ->
            handleSocialClick(context, linkMediaType, url)
        }
    )
}

fun handleSocialClick(context: Context, type: LinkMediaType, url: String?) {
    when (type) {
        LinkMediaType.FACEBOOK -> context.openFacebook()
        LinkMediaType.INSTAGRAM -> context.openInstagram()
        LinkMediaType.LINKEDIN -> context.openLinkedin()
        LinkMediaType.ANIMEAL -> context.openAnimealWebsite()
        LinkMediaType.WEB -> context.openWebsite(url!!)
    }
}

enum class LinkMediaType {
    FACEBOOK,
    INSTAGRAM,
    LINKEDIN,
    ANIMEAL,
    WEB
}