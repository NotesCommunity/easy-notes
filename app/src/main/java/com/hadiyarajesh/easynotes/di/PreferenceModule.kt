package com.hadiyarajesh.easynotes.di

import android.content.Context
import com.hadiyarajesh.easynotes.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {
    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }
}
