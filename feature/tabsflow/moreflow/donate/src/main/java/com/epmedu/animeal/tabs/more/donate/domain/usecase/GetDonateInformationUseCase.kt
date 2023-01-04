package com.epmedu.animeal.tabs.more.donate.domain.usecase

import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import com.epmedu.animeal.tabs.more.donate.domain.repository.DonateRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDonateInformationUseCase @Inject constructor(private val donateRepository: DonateRepository) {

    operator fun invoke(): Flow<ImmutableList<DonateInformation>> =
        donateRepository.getDonateInformation()
}