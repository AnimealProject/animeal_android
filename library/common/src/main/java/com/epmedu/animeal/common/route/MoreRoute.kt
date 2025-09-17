package com.epmedu.animeal.common.route

enum class MoreRoute(val allowGuest: Boolean = false) {
    More(true),
    Profile,
    Feedings,
    FAQ(true),
    About(true),
    Account,
    Donate(true),
    Terms,
    Policy
}