package com.epmedu.animeal.auth

@Deprecated(
    "Usages of this interface should be replaced with suspendCancellableCoroutine " +
        "from the extensions library",
    ReplaceWith("suspendCancellableCoroutine"),
    DeprecationLevel.WARNING
)
interface AuthRequestHandler {
    fun onSuccess(result: Any? = null)
    fun onError(exception: Exception)
}