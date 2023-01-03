package com.epmedu.animeal.tabs.more.donate.domain.repository

import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface DonateRepository {

    fun getDonateInformation(): Flow<ImmutableList<DonateInformation>>
}