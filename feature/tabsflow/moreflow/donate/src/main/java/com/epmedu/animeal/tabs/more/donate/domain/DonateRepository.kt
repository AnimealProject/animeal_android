package com.epmedu.animeal.tabs.more.donate.domain

import kotlinx.coroutines.flow.Flow

interface DonateRepository {

    fun getDonateInformation(): Flow<List<DonateInformation>>
}