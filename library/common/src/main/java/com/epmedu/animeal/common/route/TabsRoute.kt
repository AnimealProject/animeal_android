package com.epmedu.animeal.common.route

enum class TabsRoute : RouteWithArgs {
    Search,
    Favourites,
    Home,
    Analytics,
    More,
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