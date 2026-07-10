package com.jongchan.androidarchi.common.data.mediaSearch

import com.jongchan.androidarchi.common.domain.media.MediaSearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaSearchDataModule {

    @Binds
    @Singleton
    abstract fun bindMediaRepository(impl: MediaSearchRepositoryImpl): MediaSearchRepository

    companion object {

        @Provides
        @Singleton
        fun provideKakaoSearchDataSource(apiService: MediaSearchApiService): MediaSearchDataSource =
            MediaSearchDataSource(apiService)

        @Provides
        @Singleton
        fun provideKakaoSearchApiService(retrofit: Retrofit): MediaSearchApiService =
            retrofit.create(MediaSearchApiService::class.java)
    }
}
