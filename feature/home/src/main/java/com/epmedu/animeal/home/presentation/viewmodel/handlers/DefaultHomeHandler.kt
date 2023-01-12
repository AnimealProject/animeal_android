package com.epmedu.animeal.home.presentation.viewmodel.handlers

import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.FeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import javax.inject.Inject

class DefaultHomeHandler @Inject constructor(
    private val routeHandler: RouteHandler,
    private val willFeedHandler: WillFeedHandler,
    private val feedingHandler: FeedingHandler,
    private val locationHandler: LocationHandler,
    private val gpsHandler: GpsHandler
) : RouteHandler by routeHandler,
    WillFeedHandler by willFeedHandler,
    FeedingHandler by feedingHandler,
    LocationHandler by locationHandler,
    GpsHandler by gpsHandler