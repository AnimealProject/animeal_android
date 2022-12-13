package com.epmedu.animeal.home.presentation.model

sealed interface WillFeedState{
    object Dismissed: WillFeedState
    object Showing: WillFeedState
}
