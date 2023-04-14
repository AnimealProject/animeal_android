package com.epmedu.animeal.common.route

enum class TabsRoute {
    Search,
    Favourites,
    Home,
    Analytics,
    More;
    companion object {
        fun fromRoutePath(routeName: String?): TabsRoute? {
            routeName ?: return null

            return TabsRoute.values().firstOrNull {
                routeName.contains(it.name)
            }
        }
    }
}