package com.epmedu.animeal.home.presentation.viewmodel.handlers

import com.epmedu.animeal.feeding.presentation.viewmodel.handler.willfeed.WillFeedHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.camera.CameraHandler
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.TimerCancellationHandler
import javax.inject.Inject

@Suppress("LongParameterList")
internal class DefaultHomeHandler @Inject constructor(
    private val cameraHandler: CameraHandler,
    private val feedingPointHandler: FeedingPointHandler,
    private val routeHandler: com.epmedu.animeal.router.presentation.RouteHandler,
    private val willFeedHandler: WillFeedHandler,
    private val feedingHandler: FeedingHandler,
    private val locationHandler: LocationHandler,
    private val timerHandler: TimerHandler,
    private val timerCancellationHandler: TimerCancellationHandler,
    private val gpsHandler: GpsHandler,
    private val errorHandler: ErrorHandler
) : CameraHandler by cameraHandler,
    FeedingPointHandler by feedingPointHandler,
    com.epmedu.animeal.router.presentation.RouteHandler by routeHandler,
    WillFeedHandler by willFeedHandler,
    FeedingHandler by feedingHandler,
    LocationHandler by locationHandler,
    TimerHandler by timerHandler,
    TimerCancellationHandler by timerCancellationHandler,
    GpsHandler by gpsHandler,
    ErrorHandler by errorHandler