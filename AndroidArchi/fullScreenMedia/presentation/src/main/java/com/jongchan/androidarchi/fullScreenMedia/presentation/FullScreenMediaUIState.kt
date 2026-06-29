package com.jongchan.androidarchi.fullScreenMedia.presentation

import com.jongchan.androidarchi.common.entity.media.MediaItemVO
import com.jongchan.androidarchi.common.presentation.mvi.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

data class FullScreenMediaUIState(
    val isLoading: Boolean,
    // 현재 노출 중인 페이지 인덱스. ViewModel 에 보존되어 구성 변경(회전) 후에도 위치가 유지된다.
    val currentIndex: Int,
    val swipeEnabled: Boolean,
    val mediaItems: ImmutableList<MediaItemVO>,
    val favoriteUrls: ImmutableSet<String>,
) : UiState {
    companion object {
        val empty: FullScreenMediaUIState = FullScreenMediaUIState(
            isLoading = true,
            currentIndex = 0,
            swipeEnabled = false,
            mediaItems = persistentListOf(),
            favoriteUrls = persistentSetOf(),
        )

        fun ready(
            mediaItems: List<MediaItemVO>,
            initialIndex: Int,
            swipeEnabled: Boolean,
        ): FullScreenMediaUIState = FullScreenMediaUIState(
            isLoading = false,
            currentIndex = initialIndex.coerceIn(0, (mediaItems.size - 1).coerceAtLeast(0)),
            swipeEnabled = swipeEnabled,
            mediaItems = mediaItems.toImmutableList(),
            favoriteUrls = persistentSetOf(),
        )
    }
}

internal fun Set<String>.asImmutableUrls(): ImmutableSet<String> = toImmutableSet()
