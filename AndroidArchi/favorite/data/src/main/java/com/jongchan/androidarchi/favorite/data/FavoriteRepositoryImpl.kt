package com.jongchan.androidarchi.favorite.data

import com.jongchan.androidarchi.common.domain.favorite.FavoriteRepository
import com.jongchan.androidarchi.common.entity.favorite.FavoriteItemVO
import com.jongchan.androidarchi.favorite.data.local.toDto
import com.jongchan.androidarchi.favorite.data.local.toVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class FavoriteRepositoryImpl(
    private val dataSource: FavoriteDataSource,
    private val sharingScope: CoroutineScope,
) : FavoriteRepository {
    private val sharedItems: Flow<List<FavoriteItemVO>> =
        dataSource.getFavoriteMediaItemsFlow()
            .map { items -> items.map { it.toVO() } }
            .shareIn(
                scope = sharingScope,
                started = SharingStarted.WhileSubscribed(5_000),
                replay = 1,
            )

    override fun getFavoriteItemsFlow(): Flow<List<FavoriteItemVO>> = sharedItems

    override suspend fun createFavoriteItem(item: FavoriteItemVO) {
        dataSource.insertFavoriteMediaItem(item.toDto())
    }

    override suspend fun deleteFavoriteItem(url: String) {
        dataSource.deleteFavoriteMediaItem(url)
    }
}
