package com.epmedu.animeal.api.wrapper

data class ResponseError(val causes: List<Any>) : Throwable()