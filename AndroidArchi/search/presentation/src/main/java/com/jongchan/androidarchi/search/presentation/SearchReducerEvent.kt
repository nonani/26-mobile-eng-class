package com.jongchan.androidarchi.search.presentation

import com.jongchan.androidarchi.common.entity.media.MediaSearchResultVO
import com.jongchan.androidarchi.common.presentation.mvi.ReducerEvent

sealed interface SearchReducerEvent : ReducerEvent {
    data class QueryChanged(val query: String) : SearchReducerEvent
    data object Cleared : SearchReducerEvent
    data object SearchStarted : SearchReducerEvent
    data object SearchFailed : SearchReducerEvent
    data class SearchResultLoaded(val result: MediaSearchResultVO) : SearchReducerEvent
    data object LoadMoreStarted : SearchReducerEvent
    data class MorePageLoaded(val result: MediaSearchResultVO, val page: Int) : SearchReducerEvent
    data object LoadMoreFailed : SearchReducerEvent
    data class FavoritesChanged(val urls: Set<String>) : SearchReducerEvent
}
