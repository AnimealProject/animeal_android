package com.epmedu.animeal.common.timer

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow

suspend fun tickerFlow(millisInFuture: Long, countDownInterval: Long) = channelFlow {
    var timeLeft = millisInFuture
    while (timeLeft > 0) {
        trySend(timeLeft)
        timeLeft -= countDownInterval
        delay(countDownInterval)
    }
}