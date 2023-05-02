package com.epmedu.animeal.home.presentation.viewmodel.handlers.error

internal interface ErrorHandler {

    fun showError(message: String = "")
    fun hideError()
}