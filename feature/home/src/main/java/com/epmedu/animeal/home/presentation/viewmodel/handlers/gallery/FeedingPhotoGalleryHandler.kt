package com.epmedu.animeal.home.presentation.viewmodel.handlers.gallery

import com.epmedu.animeal.home.presentation.HomeScreenEvent
import kotlinx.coroutines.CoroutineScope

interface FeedingPhotoGalleryHandler {
    fun CoroutineScope.handleGalleryEvent(event: HomeScreenEvent.FeedingGalleryEvent)
}