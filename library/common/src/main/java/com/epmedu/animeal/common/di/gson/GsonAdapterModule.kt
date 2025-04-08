package com.epmedu.animeal.common.di.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object GsonAdapterModule {
    @Provides
    @Singleton
    @GsonPreferences
    fun providePreferencesGson(typeAdapters: Set<@JvmSuppressWildcards AbsGsonTypeAdapter<*>>): Gson {
        return GsonBuilder()
            .apply {
                typeAdapters.forEach { adapter -> registerTypeAdapter(adapter.clazz, adapter) }
            }
            .setPrettyPrinting()
            .create()
    }
}
