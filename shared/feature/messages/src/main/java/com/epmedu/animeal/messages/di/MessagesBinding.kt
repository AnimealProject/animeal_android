package com.epmedu.animeal.messages.di

import com.epmedu.animeal.messages.data.repository.MessageRepositoryImpl
import com.epmedu.animeal.messages.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MessagesBinding {

    @Binds
    fun bindMessageRepository(impl: MessageRepositoryImpl): MessageRepository
}
