package com.epmedu.animeal.tabs.more.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.epmedu.animeal.navigation.navigator.LocalNavigator

@Composable
fun AboutScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val facebookWebIntent = remember {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/animalprojectgeorgia/"))
    }
    val facebookAppIntent =
        remember { Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + "100226375359696")) }
    val instagramAppIntent = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.instagram.com/animalproject.ge/")
        )
    }.setPackage("com.instagram.android")
    val instagramWebIntent = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.instagram.com/animalproject.ge/")
        )
    }
    val linkedinWebIntent = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.linkedin.com/company/animalprojectgeorgia/")
        )
    }
//    TODO: update webIntent url when ready
    val webIntent = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.linkedin.com/company/animalprojectgeorgia/")
        )
    }

    BottomBarVisibility(HIDDEN)

    AboutScreenUI(
        onBack = navigator::popBackStack,
        onSocialFacebookClick = {
            try {
                context.startActivity(facebookAppIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                context.startActivity(facebookWebIntent)
            }
        },
        onSocialInstagramClick = {
            try {
                context.startActivity(instagramAppIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                context.startActivity(instagramWebIntent)
            }
        },
        onSocialLinkedinClick = {
            context.startActivity(linkedinWebIntent)
        },
        onSocialWebClick = {
            context.startActivity(webIntent)
        },
    )
}
