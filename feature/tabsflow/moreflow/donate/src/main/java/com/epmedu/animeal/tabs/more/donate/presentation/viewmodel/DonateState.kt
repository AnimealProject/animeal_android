package com.epmedu.animeal.tabs.more.donate.presentation.viewmodel

import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class DonateState(
    val donationInformation: ImmutableList<DonateInformation> = persistentListOf(),
    val donationNumberToCopy: String? = null,
    val popBackstack: Boolean = false
)