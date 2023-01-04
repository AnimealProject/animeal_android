package com.epmedu.animeal.tabs.more.donate.presentation

sealed class DonateScreenEvent {
    class DonateNumberClicked(val number: String) : DonateScreenEvent()
    object NumberIsCopied : DonateScreenEvent()
}