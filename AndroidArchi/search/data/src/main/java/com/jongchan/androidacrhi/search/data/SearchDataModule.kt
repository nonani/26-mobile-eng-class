package com.jongchan.androidacrhi.search.data

import com.jongchan.androidarchi.search.domain.SearchPageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchDataModule {

    @Provides
    @Singleton
    fun provideSearchPageRepository(
        searchPageDataSource: SearchPageDataSource,
        json: Json,
    ): SearchPageRepository =
        SearchPageRepositoryImpl(searchPageDataSource, json)

    @Provides
    @Singleton
    fun provideSearchPageDataSource(
        apiService: SearchPageApiService,
        json: Json,
    ): SearchPageDataSource =
        SearchPageDataSource(apiService, json)

    @Provides
    @Singleton
    fun provideSearchPageApiService(retrofit: Retrofit): SearchPageApiService =
        retrofit.create(SearchPageApiService::class.java)
}
