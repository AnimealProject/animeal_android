package com.epmedu.animeal.home.presentation.viewmodel.handlers

import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.camera.CameraHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.TimerCancellationHandler
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import javax.inject.Inject

internal class DefaultHomeHandler @Inject constructor(
    private val cameraHandler: CameraHandler,
    private val feedingPointHandler: FeedingPointHandler,
    private val routeHandler: RouteHandler,
    private val feedingHandler: FeedingHandler,
    private val locationHandler: LocationHandler,
    private val timerHandler: TimerHandler,
    private val timerCancellationHandler: TimerCancellationHandler,
    private val errorHandler: ErrorHandler
) : CameraHandler by cameraHandler,
    FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    FeedingHandler by feedingHandler,
    LocationHandler by locationHandler,
    TimerHandler by timerHandler,
    TimerCancellationHandler by timerCancellationHandler,
    ErrorHandler by errorHandler