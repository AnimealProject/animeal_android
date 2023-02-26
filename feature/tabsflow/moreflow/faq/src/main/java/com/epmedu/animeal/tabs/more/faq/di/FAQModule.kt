package com.epmedu.animeal.tabs.more.faq.di

import com.epmedu.animeal.api.faq.FAQApi
import com.epmedu.animeal.tabs.more.faq.data.FAQRepositoryImpl
import com.epmedu.animeal.tabs.more.faq.domain.FAQRepository
import com.epmedu.animeal.tabs.more.faq.domain.GetFAQUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object FAQModule {

    @ViewModelScoped
    @Provides
    fun providesFAQRepository(
        faqApi: FAQApi
    ): FAQRepository = FAQRepositoryImpl(faqApi)

    @ViewModelScoped
    @Provides
    fun providesGetFAQUseCase(repository: FAQRepository) = GetFAQUseCase(repository)
}