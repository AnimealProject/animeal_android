package com.epmedu.animeal.tabs.more.donate.presentation.viewmodel

import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation

data class DonateState(
    val donationInformation: List<DonateInformation> = emptyList(),
    val donationNumberToCopy: String? = null,
    val popBackstack: Boolean = false
)