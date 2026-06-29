package com.jongchan.androidarchi.fullScreenMedia.presentation

import com.jongchan.androidarchi.common.entity.media.MediaItemVO
import com.jongchan.androidarchi.common.presentation.mvi.ReducerEvent

sealed interface FullScreenMediaReducerEvent : ReducerEvent {
    data class Initialized(
        val mediaItems: List<MediaItemVO>,
        val initialIndex: Int,
        val swipeEnabled: Boolean,
    ) : FullScreenMediaReducerEvent

    data object EmptyMediaResolved : FullScreenMediaReducerEvent

    data class FavoritesChanged(val urls: Set<String>) : FullScreenMediaReducerEvent

    data class PageSelected(val index: Int) : FullScreenMediaReducerEvent
}
