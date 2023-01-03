package com.epmedu.animeal.tabs.more.donate.data

import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import com.epmedu.animeal.tabs.more.donate.domain.repository.DonateRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DonateRepositoryImpl : DonateRepository {

    override fun getDonateInformation(): Flow<ImmutableList<DonateInformation>> {
        return flowOf(
            persistentListOf(
                DonateInformation(
                    title = "Bank of Georgia",
                    paymentCredentials = "1GE82983752093855555",
                    icon = R.drawable.ic_bank_of_georgia,
                ),
                DonateInformation(
                    title = "TBC Bank",
                    paymentCredentials = "2GE82983752093855555",
                    icon = R.drawable.ic_tbc_bank,
                ),
                DonateInformation(
                    title = "PayPal",
                    paymentCredentials = "donate.me@example.com",
                    icon = R.drawable.ic_paypal,
                ),
            )
        )
    }
}