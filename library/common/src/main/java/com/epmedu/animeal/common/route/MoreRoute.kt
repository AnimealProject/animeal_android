package com.epmedu.animeal.common.route

enum class MoreRoute(val allowGuest: Boolean = false) {
    More(true),
    Account,
    Profile,
    DeleteAccount,

    Feedings,

    Terms(true),
    Policy(true),
    About(true),

    FAQ(true),
    Donate(true),

    Linebreak,
}