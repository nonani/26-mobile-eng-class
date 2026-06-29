package com.jongchan.androidarchi.common.domain.favorite

import com.jongchan.androidarchi.common.entity.favorite.FavoriteItemVO
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteItemsFlow(): Flow<List<FavoriteItemVO>>

    suspend fun createFavoriteItem(item: FavoriteItemVO)

    suspend fun deleteFavoriteItem(url: String)
}
