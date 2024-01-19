package com.epmedu.animeal.feedings.presentation.viewmodel

import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByIdUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetAllFeedingsUseCase
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.model.toFeedingModelStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class FeedingsViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getAllFeedingsUseCase: GetAllFeedingsUseCase,
    private val getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase
) : ViewModel(),
    StateDelegate<FeedingsState> by DefaultStateDelegate(initialState = FeedingsState()),
    ActionDelegate by actionDelegate {

    init {
        viewModelScope.launch { collectFeedings() }
    }

    private suspend fun collectFeedings() {
        updateState { copy(isLoading = true) }

        getAllFeedingsUseCase().map { feedings ->
            feedings.mapNotNull { feeding ->
                getFeedingPointByIdUseCase(feeding.feedingPointId)?.let { feedingPoint ->
                    createFeedingModel(feeding, feedingPoint)
                }
            }
        }.collectLatest { feedings ->
            updateState { copy(feedings = feedings.toImmutableList(), isLoading = false) }
        }
    }

    private fun createFeedingModel(
        feeding: Feeding,
        feedingPoint: FeedingPoint
    ): FeedingModel? {
        return feeding.status.toFeedingModelStatus(
            deltaTime = System.currentTimeMillis() - feeding.date.time
        )?.let {
            FeedingModel(
                id = feeding.id,
                title = feedingPoint.title,
                feeder = "${feeding.name} ${feeding.surname}",
                status = it,
                elapsedTime = DateUtils.getRelativeTimeSpanString(
                    feeding.date.time,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS
                ).toString(),
                image = feedingPoint.image
            )
        }
    }
}