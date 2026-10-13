package com.epmedu.animeal.tabs.more.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.resources.R

sealed class MoreOption(
    val route: MoreRoute,
    @StringRes val stringResource: Int,
    @DrawableRes val iconResource: Int? = null,
    val children: List<MoreOption>? = null
) {
    data class Feedings(val isIndicatorEnabled: Boolean = false) : MoreOption(
        route = MoreRoute.Feedings,
        stringResource = R.string.feedings,
        iconResource = R.drawable.more_feedings
    )

    data object FAQ : MoreOption(MoreRoute.FAQ, R.string.page_faq, R.drawable.more_faq)
    data object About : MoreOption(MoreRoute.About, R.string.page_about, R.drawable.more_about)

    private data object Profile : MoreOption(MoreRoute.Profile, R.string.page_profile, R.drawable.more_profile)
    data object DeleteAccount : MoreOption(MoreRoute.DeleteAccount, R.string.page_delete_account, R.drawable.ic_delete)
    data object Account : MoreOption(
        MoreRoute.Account,
        R.string.page_account,
        R.drawable.more_account,
        listOf(Profile, DeleteAccount)
    )

    data object Donate : MoreOption(MoreRoute.Donate, R.string.page_donate, R.drawable.more_donate)

    data object Terms : MoreOption(MoreRoute.Terms, R.string.page_terms, R.drawable.more_terms)
    data object Policy : MoreOption(MoreRoute.Policy, R.string.page_policy, R.drawable.more_policy)

    data object Linebreak : MoreOption(MoreRoute.Linebreak, 0)
}