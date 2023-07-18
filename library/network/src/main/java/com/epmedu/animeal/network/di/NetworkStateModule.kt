package com.epmedu.animeal.network.di

import android.content.Context
import android.net.ConnectivityManager
import com.epmedu.animeal.network.NetworkStateProvider
import com.epmedu.animeal.network.NetworkStateProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NetworkStateModule {
    @ViewModelScoped
    @Provides
    fun provideNetworkStateProvider(
        @ApplicationContext context: Context
    ): NetworkStateProvider = NetworkStateProviderImpl(context.getSystemService(ConnectivityManager::class.java))
}