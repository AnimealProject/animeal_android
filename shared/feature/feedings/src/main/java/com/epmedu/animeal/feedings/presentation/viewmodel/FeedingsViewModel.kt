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
import com.epmedu.animeal.feedings.domain.usecase.ApproveFeedingUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetAllFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetHasReviewedFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetViewedFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.UpdateViewedFeedingsUseCase
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.ApproveClicked
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.ErrorShown
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.UpdateCategoryEvent
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.UpdateCurrentFeeding
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.model.FeedingModelStatus
import com.epmedu.animeal.feedings.presentation.model.toFeedingModelStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FeedingsViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getAllFeedingsUseCase: GetAllFeedingsUseCase,
    private val getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase,
    private val getHasReviewedFeedingsUseCase: GetHasReviewedFeedingsUseCase,
    private val approveFeedingUseCase: ApproveFeedingUseCase,
    private val getViewedFeedingsUseCase: GetViewedFeedingsUseCase,
    private val updateViewedFeedingsUseCase: UpdateViewedFeedingsUseCase
) : ViewModel(),
    StateDelegate<FeedingsState> by DefaultStateDelegate(initialState = FeedingsState()),
    ActionDelegate by actionDelegate {

    private val allFeedings = mutableMapOf<FeedingFilterCategory, List<FeedingModel>>()
    private var feedingsJob: Job? = null

    init {
        fetchFeedings(withLoading = true)
        observeCurrentFeeding()
    }

    fun handleEvents(event: FeedingsScreenEvent) {
        when (event) {
            is UpdateCategoryEvent -> updateFeedingsCategory(event.category)
            is UpdateCurrentFeeding -> updateCurrentFeeding(event.feeding)
            is ApproveClicked -> approveFeeding(event.feeding)
            is ErrorShown -> hideError()
        }
    }

    private suspend fun collectFeedings(withLoading: Boolean) {
        if (withLoading) updateState { copy(isListLoading = true) }

        combine(
            getAllFeedingsUseCase(shouldFetch = true),
            getHasReviewedFeedingsUseCase(),
            getViewedFeedingsUseCase()
        ) { feedings, hasReviewedFeedings, viewedFeedingIds ->
            val feedingModels = feedings.mapNotNull { feeding ->
                getFeedingPointByIdUseCase(feeding.feedingPointId)?.let { feedingPoint ->
                    createFeedingModel(feeding, feedingPoint)
                }
            }
            mapFeedingsByCategory(feedingModels)

            val feedingsFiltered =
                allFeedings.getOrDefault(state.feedingsCategory, emptyList()).toImmutableList()

            val currentFeeding = getCurrentFeeding(feedingModels, feedingsFiltered, viewedFeedingIds)
            val feedingsCategory = currentFeeding?.status?.let {
                toFilterCategory(it)
            } ?: state.feedingsCategory

            updateState {
                copy(
                    currentFeeding = currentFeeding,
                    feedingsFiltered = feedingsFiltered,
                    feedingsCategory = feedingsCategory,
                    hasReviewedFeedings = hasReviewedFeedings,
                    isListLoading = false,
                    isLockedAndLoading = false
                )
            }
        }.collect()
    }

    private fun getCurrentFeeding(
        feedingModels: List<FeedingModel>,
        feedingsFiltered: List<FeedingModel>,
        viewedFeedingIds: Set<String>
    ): FeedingModel? {
        return when {
            state.isLockedAndLoading && feedingsFiltered.isNotEmpty() -> {
                // only after reviewing feeding from the detailed view,
                // we need to show the next feeding
                when {
                    state.currentFeeding != null -> feedingsFiltered.first()
                    else -> null
                }
            }

            state.isListLoading && state.feedingsCategory == FeedingFilterCategory.PENDING -> {
                feedingsFiltered.firstOrNull { viewedFeedingIds.contains(it.id).not() }
            }

            else -> {
                feedingModels.find { it.id == state.currentFeeding?.id }
            }
        }
    }

    private fun mapFeedingsByCategory(feedings: List<FeedingModel>) {
        val pendingFeedings = mutableListOf<FeedingModel>()
        val approvedFeedings = mutableListOf<FeedingModel>()
        val rejectedFeedings = mutableListOf<FeedingModel>()
        val outdatedFeedings = mutableListOf<FeedingModel>()

        feedings.forEach { feeding ->
            when (toFilterCategory(feeding.status)) {
                FeedingFilterCategory.PENDING -> pendingFeedings.add(feeding)
                FeedingFilterCategory.APPROVED -> approvedFeedings.add(feeding)
                FeedingFilterCategory.REJECTED -> rejectedFeedings.add(feeding)
                FeedingFilterCategory.OUTDATED -> outdatedFeedings.add(feeding)
            }
        }

        allFeedings.clear()
        allFeedings[FeedingFilterCategory.PENDING] = pendingFeedings
        allFeedings[FeedingFilterCategory.APPROVED] = approvedFeedings
        allFeedings[FeedingFilterCategory.REJECTED] = rejectedFeedings
        allFeedings[FeedingFilterCategory.OUTDATED] = outdatedFeedings
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
                feedingPointId = feeding.feedingPointId,
                title = feedingPoint.title,
                feeder = "${feeding.feeder?.name.orEmpty()} ${feeding.feeder?.surname.orEmpty()}",
                status = feedingStatus,
                elapsedTime = DateUtils.getRelativeTimeSpanString(
                    feeding.date.time,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS
                ).toString(),
                image = feedingPoint.image,
                photos = feeding.photos.toImmutableList(),
                reviewedBy = feeding.reviewedBy?.let { "${it.name} ${it.surname}" }
                    .takeIf { feedingStatus == FeedingModelStatus.APPROVED || feedingStatus == FeedingModelStatus.REJECTED },
                rejectionReason = feeding.rejectionReason
                    .takeIf { feedingStatus == FeedingModelStatus.REJECTED }
            )
        } else {
            null
        }
    }

    private fun updateFeedingsCategory(feedingsCategory: FeedingFilterCategory) {
        if (state.isListLoading.not()) {
            updateState {
                copy(
                    feedingsCategory = feedingsCategory,
                    feedingsFiltered = allFeedings.getOrDefault(feedingsCategory, emptyList())
                        .toImmutableList()
                )
            }
        }
    }

    private fun updateCurrentFeeding(feeding: FeedingModel?) {
        updateState { copy(currentFeeding = feeding) }
    }

    private fun approveFeeding(feeding: FeedingModel) {
        updateState { copy(isLockedAndLoading = true) }

        viewModelScope.launch {
            performAction(
                action = {
                    approveFeedingUseCase(feedingPointId = feeding.feedingPointId)
                },
                onSuccess = {
                    fetchFeedings(withLoading = false)
                },
                onError = {
                    updateState { copy(isLockedAndLoading = false, isError = true) }
                }
            )
        }
    }

    private fun fetchFeedings(withLoading: Boolean) {
        feedingsJob?.cancel()
        feedingsJob = viewModelScope.launch { collectFeedings(withLoading) }
    }

    private fun observeCurrentFeeding() {
        viewModelScope.launch {
            stateFlow.map { it.currentFeeding }
                .collect { currentFeeding ->
                    currentFeeding?.let {
                        updateViewedFeedingsUseCase(it.id)
                    }
                }
        }
    }

    private fun hideError() {
        updateState { copy(isError = false) }
    }
}