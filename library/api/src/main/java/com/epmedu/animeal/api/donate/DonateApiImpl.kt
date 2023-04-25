package com.epmedu.animeal.api.donate

import com.amplifyframework.datastore.generated.model.BankAccount
import com.epmedu.animeal.api.extensions.getModelList
import kotlinx.coroutines.flow.Flow

class DonateApiImpl : DonateApi {

    override fun getBankAccounts(): Flow<List<BankAccount>> {
        return getModelList()
    }
}