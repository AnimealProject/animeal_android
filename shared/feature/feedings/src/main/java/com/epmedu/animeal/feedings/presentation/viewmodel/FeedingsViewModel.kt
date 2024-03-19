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
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.model.FeedingModelStatus
import com.epmedu.animeal.feedings.presentation.model.toFeedingModelStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FeedingsViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getAllFeedingsUseCase: GetAllFeedingsUseCase,
    private val getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase
) : ViewModel(),
    StateDelegate<FeedingsState> by DefaultStateDelegate(initialState = FeedingsState()),
    ActionDelegate by actionDelegate {

    private var allFeedings: List<FeedingModel> = emptyList()

    init {
        viewModelScope.launch { collectFeedings() }
    }

    fun handleEvents(event: FeedingsScreenEvent) {
        when (event) {
            is FeedingsScreenEvent.UpdateCategoryEvent -> updateFeedingsCategory(event.category)
        }
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
            allFeedings = feedings
            updateState {
                copy(
                    feedingsFiltered = feedings.filter {
                        state.feedingsCategory == toFilterCategory(it.status)
                    }.toImmutableList(),
                    isLoading = false
                )
            }
        }
    }

    private fun toFilterCategory(feedingStatus: FeedingModelStatus): FeedingFilterCategory =
        when (feedingStatus) {
            FeedingModelStatus.APPROVED,
            FeedingModelStatus.AUTO_APPROVED -> FeedingFilterCategory.APPROVED
            FeedingModelStatus.PENDING_RED,
            FeedingModelStatus.PENDING_ORANGE,
            FeedingModelStatus.PENDING_GREY -> FeedingFilterCategory.PENDING
            FeedingModelStatus.REJECTED -> FeedingFilterCategory.REJECTED
            FeedingModelStatus.OUTDATED -> FeedingFilterCategory.OUTDATED
        }

    private fun createFeedingModel(
        feeding: Feeding,
        feedingPoint: FeedingPoint
    ): FeedingModel? {
        val feedingStatus = feeding.status.toFeedingModelStatus(
            deltaTime = System.currentTimeMillis() - feeding.date.time,
            isFeederTrusted = feeding.feeder?.isTrusted == true
        )

        return if (feeding.feedingPointId == feedingPoint.id && feedingStatus != null) {
            FeedingModel(
                id = feeding.id,
                title = feedingPoint.title,
                feeder = "${feeding.feeder?.name.orEmpty()} ${feeding.feeder?.surname.orEmpty()}",
                status = feedingStatus,
                elapsedTime = DateUtils.getRelativeTimeSpanString(
                    feeding.date.time,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS
                ).toString(),
                image = feedingPoint.image,
                photos = feeding.photos.toImmutableList()
            )
        } else {
            null
        }
    }

    private fun updateFeedingsCategory(feedingsCategory: FeedingFilterCategory) {
        if (state.isLoading.not()) {
            updateState {
                copy(
                    feedingsCategory = feedingsCategory,
                    feedingsFiltered = allFeedings.filter {
                        feedingsCategory == toFilterCategory(it.status)
                    }.toImmutableList()
                )
            }
        }
    }
}