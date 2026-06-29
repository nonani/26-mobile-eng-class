package com.jongchan.androidarchi.favorite.presentation

import com.jongchan.androidarchi.common.entity.favorite.FavoriteItemVO
import com.jongchan.androidarchi.common.presentation.searchList.MediaItemUiState
import com.jongchan.androidarchi.common.presentation.searchList.formatKakaoDateTime
import com.jongchan.androidarchi.common.presentation.mvi.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class FavoriteUIState(
    val isLoading: Boolean,
    val favoriteItemList: ImmutableList<MediaItemUiState>,
) : UiState {

    companion object {
        val empty = FavoriteUIState(
            isLoading = true,
            favoriteItemList = persistentListOf(),
        )

        fun fromItems(items: List<FavoriteItemVO>): FavoriteUIState = FavoriteUIState(
            isLoading = false,
            favoriteItemList = items.map { it.toUiState() }.toImmutableList(),
        )

        // 보관함 화면의 항목은 모두 즐겨찾기 상태다.
        private fun FavoriteItemVO.toUiState(): MediaItemUiState = MediaItemUiState(
            mediaType = type,
            title = title,
            thumbnailUrl = thumbnailUrl,
            contentsUrl = urlKey,
            dateTimeText = formatKakaoDateTime(dateTime),
            isFavorite = true,
        )
    }
}
