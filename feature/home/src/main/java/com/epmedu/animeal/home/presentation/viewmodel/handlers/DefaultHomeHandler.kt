package com.epmedu.animeal.home.presentation.viewmodel.handlers

import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import javax.inject.Inject

class DefaultHomeHandler @Inject constructor(
    private val routeHandler: RouteHandler,
    private val willFeedHandler: WillFeedHandler
) : RouteHandler by routeHandler, WillFeedHandler by willFeedHandler