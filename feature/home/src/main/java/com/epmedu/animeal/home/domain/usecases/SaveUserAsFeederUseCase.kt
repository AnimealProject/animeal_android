package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveUserAsFeederUseCase @Inject constructor(
    private val feedingPointRepository: FeedingPointRepository,
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(feedingPointId: String) = flow {
        profileRepository.getProfile().map { profile ->
            feedingPointRepository.saveUserAsCurrentFeeder(
                profile,
                feedingPointId
            )
        }.catch {
            emit(false)
        }.collect {
            emit(true)
        }
    }
}