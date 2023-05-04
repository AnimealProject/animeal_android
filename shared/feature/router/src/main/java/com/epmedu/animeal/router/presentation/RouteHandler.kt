package com.epmedu.animeal.router.presentation

interface RouteHandler {

    fun handleRouteEvent(event: RouteEvent)

    fun startRoute()

    fun stopRoute()
}