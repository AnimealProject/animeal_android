package com.epmedu.animeal.api.donate

import com.amplifyframework.datastore.generated.model.BankAccount
import com.epmedu.animeal.api.AnimealApi
import kotlinx.coroutines.flow.Flow

class DonateApiImpl(
    private val animealApi: AnimealApi
) : DonateApi {

    override fun getBankAccounts(): Flow<List<BankAccount>> {
        return animealApi.getModelList(BankAccount::class.java)
    }
}