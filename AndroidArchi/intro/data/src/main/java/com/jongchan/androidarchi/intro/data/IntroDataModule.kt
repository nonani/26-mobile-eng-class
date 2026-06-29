package com.jongchan.androidarchi.intro.data

import com.jongchan.androidarchi.intro.domain.IntroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IntroDataModule {

    @Provides
    @Singleton
    fun provideIntroRepository(dataSource: IntroDataSource): IntroRepository =
        IntroRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideIntroDataSource(apiService: IntroApiService): IntroDataSource =
        IntroDataSource(apiService)

    @Provides
    @Singleton
    fun provideIntroApiService(retrofit: Retrofit): IntroApiService =
        retrofit.create(IntroApiService::class.java)
}
