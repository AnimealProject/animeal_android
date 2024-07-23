package com.epmedu.animeal.tabs.more.presentation.model

import androidx.annotation.StringRes
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.resources.R

sealed class MoreOption(
    val route: MoreRoute,
    @StringRes val stringResource: Int
) {
    data class Feedings(val isIndicatorEnabled: Boolean = false) : MoreOption(
        route = MoreRoute.Feedings,
        stringResource = R.string.feedings
    )

    data object Profile : MoreOption(MoreRoute.Profile, R.string.page_profile)
    data object FAQ : MoreOption(MoreRoute.FAQ, R.string.page_faq)
    data object About : MoreOption(MoreRoute.About, R.string.page_about_detailed)
    data object Account : MoreOption(MoreRoute.Account, R.string.page_account)
    data object Donate : MoreOption(MoreRoute.Donate, R.string.page_donate)
}