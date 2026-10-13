package com.epmedu.animeal.messages.di

import com.epmedu.animeal.messages.domain.repository.MessageRepository
import com.epmedu.animeal.messages.domain.usecase.SendIssueMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MessagesDomainModule {

    @ViewModelScoped
    @Provides
    fun providesSendIssueMessageUseCase(
        repo: MessageRepository,
    ) = SendIssueMessageUseCase(repo)
}