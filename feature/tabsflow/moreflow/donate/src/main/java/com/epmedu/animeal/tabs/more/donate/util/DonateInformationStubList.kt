package com.epmedu.animeal.tabs.more.donate.util

import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import kotlinx.collections.immutable.persistentListOf

internal val tbcBankInformation = DonateInformation(
    title = "TBC Bank",
    paymentCredentials = "2GE82983752093855555",
    icon = null,
)

internal val payPalInformation = DonateInformation(
    title = "PayPal",
    paymentCredentials = "donate.me@example.com",
    icon = null,
)

internal val donateInformationStubList = persistentListOf(
    tbcBankInformation,
    payPalInformation
)
