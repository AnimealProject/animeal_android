package com.epmedu.animeal.tabs.more.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.extensions.openWebsite
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibility
import com.epmedu.animeal.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.presentation.viewmodel.MoreViewModel

@Composable
fun MoreScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel = hiltViewModel<MoreViewModel>()
    val state by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current

    val optionNameToUrl: Map<String, String> = stringArrayResource(R.array.about_links)
        .map {
            val (name, _, url) = it.split("|")
            name to url
        }
        .toMap()

    BottomBarVisibility(SHOWN)

    MoreScreenUi(
        state = state,
        onNavigate = {
            when (it) {
                MoreRoute.Terms.name,
                MoreRoute.Policy.name -> optionNameToUrl[it]?.let(context::openWebsite)
                else -> navigator.navigate(it)
            }
        }
    )
}
