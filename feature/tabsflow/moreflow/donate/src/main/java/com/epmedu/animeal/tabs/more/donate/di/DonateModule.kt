package com.epmedu.animeal.tabs.more.donate.di

import com.epmedu.animeal.tabs.more.donate.data.DonateRepositoryImpl
import com.epmedu.animeal.tabs.more.donate.domain.repository.DonateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DonateModule {

    @ViewModelScoped
    @Provides
    fun provideDonateRepository(): DonateRepository = DonateRepositoryImpl()
}