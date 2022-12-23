package com.epmedu.animeal.tabs.more.donate.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface DonateRepository {

    fun getDonateInformation(): Flow<ImmutableList<DonateInformation>>
}