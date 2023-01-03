package com.epmedu.animeal.tabs.more.faq.di

import com.epmedu.animeal.tabs.more.faq.data.FAQRepositoryImpl
import com.epmedu.animeal.tabs.more.faq.domain.FAQRepository
import com.epmedu.animeal.tabs.more.faq.domain.GetFAQUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object FAQModule {

    @Provides
    fun providesFAQRepository(): FAQRepository = FAQRepositoryImpl()

    @Provides
    fun providesGetFAQUseCase(repository: FAQRepository) = GetFAQUseCase(repository)
}