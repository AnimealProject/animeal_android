package com.epmedu.animeal.extensions

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine

suspend inline fun <T> suspendCancellableCoroutine(
    crossinline block: CancellableContinuation<T>.() -> Unit
): T {
    return suspendCancellableCoroutine { block(it) }
}