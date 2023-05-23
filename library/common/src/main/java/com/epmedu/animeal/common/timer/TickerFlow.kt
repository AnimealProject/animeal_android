package com.epmedu.animeal.common.timer

import android.os.CountDownTimer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

suspend fun tickerFlow(millisInFuture: Long, countDownInterval: Long) = callbackFlow {
    val timer = object : CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            trySend(millisUntilFinished)
        }

        override fun onFinish() {
            close()
        }
    }
    timer.start()
    awaitClose { timer.cancel() }
}