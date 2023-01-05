package com.epmedu.animeal.tabs.more.donate.domain

data class DonateInformation(
    val title: String,
    val paymentCredentials: String,
    // replace with string url when backend is implemented
    val icon: Int,
)