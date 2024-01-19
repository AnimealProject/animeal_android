package com.epmedu.animeal.tabs.more.donate.domain

import com.epmedu.animeal.networkstorage.domain.NetworkFile

data class DonateInformation(
    val title: String,
    val paymentCredentials: String,
    val icon: NetworkFile?,
)