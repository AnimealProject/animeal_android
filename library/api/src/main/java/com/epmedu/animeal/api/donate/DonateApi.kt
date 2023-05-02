package com.epmedu.animeal.api.donate

import com.amplifyframework.datastore.generated.model.BankAccount
import kotlinx.coroutines.flow.Flow

interface DonateApi {

    fun getBankAccounts(): Flow<List<BankAccount>>
}