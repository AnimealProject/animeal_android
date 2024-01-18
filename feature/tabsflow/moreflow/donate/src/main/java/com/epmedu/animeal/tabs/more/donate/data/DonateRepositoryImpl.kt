package com.epmedu.animeal.tabs.more.donate.data

import com.epmedu.animeal.api.donate.DonateApi
import com.epmedu.animeal.networkstorage.data.api.StorageApi
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.tabs.more.donate.data.mapper.toDomain
import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import com.epmedu.animeal.tabs.more.donate.domain.repository.DonateRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DonateRepositoryImpl(
    private val donateApi: DonateApi,
    private val storageApi: StorageApi
) : DonateRepository {

    override fun getDonateInformation(): Flow<ImmutableList<DonateInformation>> {
        return donateApi.getBankAccounts().map { bankAccounts ->
            bankAccounts
                .filter { it.enabled }
                .map { bankAccount ->
                    bankAccount.toDomain(
                        icon = with(bankAccount.cover) {
                            NetworkFile(
                                name = this,
                                url = storageApi.getUrlFrom(fileName = this)
                            )
                        }
                    )
                }
                .toImmutableList()
        }
    }
}