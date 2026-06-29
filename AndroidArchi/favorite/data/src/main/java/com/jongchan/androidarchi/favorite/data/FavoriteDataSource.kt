package com.jongchan.androidarchi.favorite.data

import com.jongchan.androidarchi.favorite.data.local.FavoriteItemDto
import com.jongchan.androidarchi.favorite.data.local.FavoriteKVStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class FavoriteDataSource(
    private val favoriteKvStorage: FavoriteKVStorage,
    private val json: Json,
) {
    fun getFavoriteMediaItemsFlow(): Flow<List<FavoriteItemDto>> =
        favoriteKvStorage.observeString(FavoriteKVStorage.KEY_SAVED_FAVORITE_ITEMS)
            .map { raw -> raw.toFavoriteItems() }

    suspend fun insertFavoriteMediaItem(item: FavoriteItemDto) {
        favoriteKvStorage.insert(item)
    }

    suspend fun deleteFavoriteMediaItem(url: String) {
        favoriteKvStorage.deleteByUrl(url)
    }

    private fun String?.toFavoriteItems(): List<FavoriteItemDto> {
        if (isNullOrBlank()) return emptyList()
        return runCatching { json.decodeFromString<List<FavoriteItemDto>>(this) }
            .getOrDefault(emptyList())
    }
}
