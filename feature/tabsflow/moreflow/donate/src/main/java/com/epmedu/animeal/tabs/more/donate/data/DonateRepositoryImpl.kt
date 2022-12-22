package com.epmedu.animeal.tabs.more.donate.data

import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import com.epmedu.animeal.tabs.more.donate.domain.DonateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DonateRepositoryImpl : DonateRepository {

    override fun getDonateInformation(): Flow<List<DonateInformation>> {
        return flowOf(
            listOf(
                DonateInformation(
                    title = "Bank of Georgia",
                    number = "1GE82983752093855555",
                    icon = R.drawable.ic_bank_of_georgia,
                ),
                DonateInformation(
                    title = "TBC Bank",
                    number = "2GE82983752093855555",
                    icon = R.drawable.ic_tbc_bank,
                ),
                DonateInformation(
                    title = "PayPal",
                    number = "3GE82983752093855555",
                    icon = R.drawable.ic_paypal,
                ),
            )
        )
    }
}