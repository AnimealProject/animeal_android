package com.epmedu.animeal.common.route

enum class TabsRoute(val allowGuest: Boolean = false) : RouteWithArgs {
    Search,
    Favourites,
    Home(true),
    Analytics,
    More(true),
    Feedings;
    companion object {
        fun fromRoutePath(routeName: String?): TabsRoute? {
            routeName ?: return null

            return entries.firstOrNull {
                routeName.contains(it.name)
            }
        }
    }
}