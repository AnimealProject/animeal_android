package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveUserAsFeederUseCase @Inject constructor(
    private val feedingPointRepository: FeedingPointRepository,
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(feedingPointId: Int) = flow {
        val profile = profileRepository.getProfile()
        val saveAsCurrentFeeder =
            feedingPointRepository.saveUserAsCurrentFeeder(profile.first(), feedingPointId)
        saveAsCurrentFeeder.catch {
            emit(false)
        }.collect {
            emit(it)
        }
    }
}