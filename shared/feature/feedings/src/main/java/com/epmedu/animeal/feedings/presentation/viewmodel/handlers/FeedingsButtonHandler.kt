package com.epmedu.animeal.feedings.presentation.viewmodel.handlers

import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import kotlinx.coroutines.flow.Flow

interface FeedingsButtonHandler {

    fun getFeedingsButtonState(): Flow<FeedingsButtonState>
}