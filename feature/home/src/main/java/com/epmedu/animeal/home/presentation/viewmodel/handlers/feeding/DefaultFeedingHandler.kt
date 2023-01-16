package com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.domain.usecases.SaveUserAsFeederUseCase
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultFeedingHandler @Inject internal constructor(
    private val repository: FeedingPointRepository,
    private val saveUserAsFeederUseCase: SaveUserAsFeederUseCase,
    stateDelegate: StateDelegate<HomeState>
) : FeedingHandler, StateDelegate<HomeState> by stateDelegate {

    override suspend fun fetchFeedingPoints() {
        repository.getAllFeedingPoints().collect {
            updateState {
                copy(
                    feedingPoints = it.map { feedingPoint ->
                        FeedingPointModel(feedingPoint)
                    }.toImmutableList()
                )
            }
        }
    }

    override fun hideFeedingPointsButOne(feedingPoint: FeedingPointModel) {
        updateState {
            copy(feedingPoints = persistentListOf(feedingPoint))
        }
    }

    override fun saveFeeder(): Flow<Boolean> {
        return saveUserAsFeederUseCase(
            state.currentFeedingPoint?.id ?: return flow { emit(false) }
        )
    }
}