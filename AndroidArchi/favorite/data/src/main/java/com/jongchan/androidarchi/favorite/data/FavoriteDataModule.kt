package com.jongchan.androidarchi.favorite.data

import android.content.Context
import com.jongchan.androidarchi.common.domain.favorite.FavoriteRepository
import com.jongchan.androidarchi.favorite.data.local.FavoriteKVStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteDataModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    companion object {

        private const val PREFS_NAME = "androidarchi_prefs"

        private val sharedPreferenceOption: Json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

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
}
