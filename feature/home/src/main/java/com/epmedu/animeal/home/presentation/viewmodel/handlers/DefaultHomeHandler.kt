package com.epmedu.animeal.home.presentation.viewmodel.handlers

import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.ErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.FeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import javax.inject.Inject

@Suppress("LongParameterList")
internal class DefaultHomeHandler @Inject constructor(
    private val feedingPointHandler: FeedingPointHandler,
    private val routeHandler: RouteHandler,
    private val willFeedHandler: WillFeedHandler,
    private val feedingHandler: FeedingHandler,
    private val locationHandler: LocationHandler,
    private val gpsHandler: GpsHandler,
    private val errorHandler: ErrorHandler
) : FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    WillFeedHandler by willFeedHandler,
    FeedingHandler by feedingHandler,
    LocationHandler by locationHandler,
    GpsHandler by gpsHandler,
    ErrorHandler by errorHandler