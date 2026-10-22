package com.epmedu.animeal.push.di

import com.epmedu.animeal.push.data.repository.PushRepositoryImpl
import com.epmedu.animeal.push.domain.repository.PushRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface PushBinding {

    @Binds
    fun bindPushRepository(impl: PushRepositoryImpl): PushRepository
}
