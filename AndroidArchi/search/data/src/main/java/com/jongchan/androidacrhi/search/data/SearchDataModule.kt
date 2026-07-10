package com.jongchan.androidacrhi.search.data

import com.jongchan.androidarchi.search.domain.SearchPageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchDataModule {

    @Binds
    @Singleton
    abstract fun bindSearchPageRepository(impl: SearchPageRepositoryImpl): SearchPageRepository

    companion object {

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
}
