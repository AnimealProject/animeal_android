package com.epmedu.animeal.home.presentation.model

sealed interface CameraState {
    object Disabled : CameraState
    object LoadingImageToServer : CameraState
    object Enabled : CameraState
}