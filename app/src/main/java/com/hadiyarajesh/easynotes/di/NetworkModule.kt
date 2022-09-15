package com.hadiyarajesh.easynotes.di

import com.hadiyarajesh.easynotes.BuildConfig
import com.hadiyarajesh.easynotes.network.adapter.InstantAdapter
import com.hadiyarajesh.easynotes.network.api.NoteApi
import com.hadiyarajesh.flower_retrofit.FlowerCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val moshi: Moshi = Moshi.Builder()
        .add(InstantAdapter())
        .build()

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(provideLoggingInterceptor())
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(FlowerCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteApi(retrofit: Retrofit): NoteApi = retrofit.create(NoteApi::class.java)
}
