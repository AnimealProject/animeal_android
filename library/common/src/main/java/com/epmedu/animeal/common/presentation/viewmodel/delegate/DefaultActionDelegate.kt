package com.epmedu.animeal.common.presentation.viewmodel.delegate

import android.util.Log
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class DefaultActionDelegate(
    private val dispatchers: Dispatchers
) : ActionDelegate {

    override suspend fun performAction(
        action: suspend () -> ActionResult<Unit>,
        onSuccess: suspend () -> Unit,
        onError: suspend () -> Unit,
        onStart: suspend () -> Unit,
        onFinish: suspend () -> Unit
    ) {
        onStart()
        coroutineScope {
            when (val result = withContext(dispatchers.IO) { action() }) {
                is ActionResult.Success<*> -> {
                    onSuccess()
                }
                is ActionResult.Failure -> {
                    onError()
                    Log.e(LOG_TAG, result.error.toString())
                }
            }
        }
        onFinish()
    }

    override suspend fun <T> performAction(
        action: suspend () -> ActionResult<T>,
        onSuccess: suspend (T) -> Unit,
        onError: () -> Unit
    ) {
        coroutineScope {
            when (val result = withContext(dispatchers.IO) { action() }) {
                is ActionResult.Success<*> -> {
                    onSuccess(result.result as T)
                }
                is ActionResult.Failure -> {
                    onError()
                    Log.e(LOG_TAG, result.error.toString())
                }
            }
        }
    }

    override suspend fun <T> performAction(action: suspend () -> T): T {
        return coroutineScope {
            withContext(dispatchers.IO) { action() }
        }
    }

    private companion object {
        const val LOG_TAG = "DefaultActionDelegate"
    }
}