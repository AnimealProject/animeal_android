package com.epmedu.animeal.tabs.more.donate.data.mapper

import com.amplifyframework.datastore.generated.model.BankAccount
import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation

internal fun BankAccount.toDomain(
    iconUrl: String
) = DonateInformation(
    title = name,
    paymentCredentials = value,
    iconUrl = iconUrl
)