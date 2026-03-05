package com.epmedu.animeal.tabs.more.presentation.viewmodel

import com.epmedu.animeal.tabs.more.presentation.model.MoreOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class MoreState(
    val generalOptions: ImmutableList<MoreOption> = persistentListOf(),
    val moderationOptions: ImmutableList<MoreOption> = persistentListOf(),
    val titleResource: Int? = null,
    val toastToShow: MoreToast? = null,
    val isNavigatingToOnboarding: Boolean = false
)

internal enum class MoreToast {
    SuccessfulLogout, SuccessfulDelete
}