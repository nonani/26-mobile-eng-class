package com.jongchan.androidarchi.favorite.data

import android.content.Context
import com.jongchan.androidarchi.common.domain.coroutine.IoScope
import com.jongchan.androidarchi.common.domain.favorite.FavoriteRepository
import com.jongchan.androidarchi.favorite.data.local.FavoriteKVStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteDataModule {

    private const val PREFS_NAME = "androidarchi_prefs"

    private val sharedPreferenceOption: Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        dataSource: FavoriteDataSource,
        @IoScope sharingScope: CoroutineScope,
    ): FavoriteRepository = FavoriteRepositoryImpl(dataSource, sharingScope)

    @Provides
    @Singleton
    fun provideFavoriteDataSource(favoriteKvStorage: FavoriteKVStorage): FavoriteDataSource =
        FavoriteDataSource(favoriteKvStorage, sharedPreferenceOption)

    @Provides
    @Singleton
    fun provideKVLocalStorage(@ApplicationContext context: Context): FavoriteKVStorage =
        FavoriteKVStorage(
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE),
            json = sharedPreferenceOption,
        )
}
