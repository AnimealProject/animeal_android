package com.epmedu.animeal.tabs.more.presentation.model

import androidx.annotation.StringRes
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.resources.R

sealed class MoreOption(
    val route: MoreRoute,
    @StringRes val stringResource: Int
) {
    object Profile : MoreOption(MoreRoute.Profile, R.string.page_profile)
    object Feedings : MoreOption(MoreRoute.Feedings, R.string.feedings)
    object FAQ : MoreOption(MoreRoute.FAQ, R.string.page_faq)
    object About : MoreOption(MoreRoute.About, R.string.page_about_detailed)
    object Account : MoreOption(MoreRoute.Account, R.string.page_account)
    object Donate : MoreOption(MoreRoute.Donate, R.string.page_donate)
}