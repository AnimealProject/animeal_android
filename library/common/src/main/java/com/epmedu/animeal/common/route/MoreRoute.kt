package com.epmedu.animeal.common.route

enum class MoreRoute(val allowGuest: Boolean = false) {
    More(true),
    Profile,
    FAQ(true),
    Donate(true),
    About(true),
    Terms(true),
    Policy(true),
    Account,
    Feedings
}