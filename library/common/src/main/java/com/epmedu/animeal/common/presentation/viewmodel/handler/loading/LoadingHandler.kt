package com.epmedu.animeal.common.presentation.viewmodel.handler.loading

import kotlinx.coroutines.flow.StateFlow

interface LoadingHandler {
    val isLoading: StateFlow<Boolean>

    fun showLoading()

    fun hideLoading()
}