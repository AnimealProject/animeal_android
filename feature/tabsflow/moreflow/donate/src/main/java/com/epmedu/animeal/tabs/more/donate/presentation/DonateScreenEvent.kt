package com.epmedu.animeal.tabs.more.donate.presentation

sealed class DonateScreenEvent {
    object BackClicked : DonateScreenEvent()
    class DonateNumberClicked(val number: String) : DonateScreenEvent()
    object NumberIsCopied : DonateScreenEvent()
}