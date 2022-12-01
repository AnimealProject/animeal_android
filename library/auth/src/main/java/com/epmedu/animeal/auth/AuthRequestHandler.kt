package com.epmedu.animeal.auth

interface AuthRequestHandler {
    fun onSuccess(result: Any? = null)
    fun onError(exception: Exception)
}