package com.epmedu.animeal.tabs.more.account.viewmodel

internal data class AccountScreenState(
    val toastToShow: AccountToast? = null,
    val isNavigatingToOnboarding: Boolean = false
)

internal enum class AccountToast {
    SuccessfulLogout, SuccessfulDelete
}
