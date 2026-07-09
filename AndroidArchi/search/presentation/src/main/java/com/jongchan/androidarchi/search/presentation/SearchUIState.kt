package com.jongchan.androidarchi.search.presentation

import com.jongchan.androidarchi.common.domain.sdui.SDUIViewTypeVO
import com.jongchan.androidarchi.common.entity.media.MediaItemVO
import com.jongchan.androidarchi.common.entity.media.MediaSearchResultVO
import com.jongchan.androidarchi.common.presentation.mvi.UiState
import com.jongchan.androidarchi.common.presentation.searchList.MediaItemUiState
import com.jongchan.androidarchi.common.presentation.searchList.formatKakaoDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

data class SearchUIState(
    val sduiViewItems: List<SDUIViewTypeVO>?,
    val query: String,
    val isLoading: Boolean,
    val isLoadingMore: Boolean,
    val isLastPage: Boolean,
    val hasSearched: Boolean,
    val currentPage: Int,
    val searchItemList: ImmutableList<MediaItemUiState>,
    val favoriteUrls: ImmutableSet<String>,
    val rawMediaItems: ImmutableList<MediaItemVO>,
) : UiState {

    fun setSDUIViews(sduiViewItems: List<SDUIViewTypeVO>?): SearchUIState = copy(
        sduiViewItems = sduiViewItems,
    )

    fun rawItemByUrl(url: String): MediaItemVO? = rawMediaItems.firstOrNull { it.urlKey == url }

    /**
     * 다음 페이지 결과를 기존 리스트 뒤에 이어 붙인다. 페이지 간 재정렬은 하지 않는다.
     * 이미 노출 중인 url 은 LazyColumn key 충돌을 막기 위해 제외한다.
     * 응답의 [MediaSearchResultVO.isEnd] 가 true 거나 빈 페이지면 마지막 페이지로 표시해 추가 로드를 멈춘다.
     */
    fun appendPage(result: MediaSearchResultVO, page: Int): SearchUIState {
        val existingUrls = rawMediaItems.mapTo(HashSet(rawMediaItems.size)) { it.urlKey }
        val newItems = result.items.filterNot { it.urlKey in existingUrls }
        return copy(
            isLoadingMore = false,
            isLastPage = result.isEnd || result.items.isEmpty(),
            currentPage = page,
            searchItemList = (searchItemList + newItems.map { it.toUiState(favoriteUrls) }).toImmutableList(),
            rawMediaItems = (rawMediaItems + newItems).toImmutableList(),
        )
    }

    /** 즐겨찾기 url 집합 변경을 노출 중인 아이템들에 접어 반영한다(상태가 분기를 끝내고 Compose 는 반영만). */
    fun withFavorites(urls: ImmutableSet<String>): SearchUIState = copy(
        favoriteUrls = urls,
        searchItemList = searchItemList
            .map { it.copy(isFavorite = it.contentsUrl in urls) }
            .toImmutableList(),
    )

    companion object {
        const val FIRST_PAGE = 1

        val empty = SearchUIState(
            sduiViewItems = emptyList(),
            query = "",
            isLoading = false,
            isLoadingMore = false,
            isLastPage = false,
            hasSearched = false,
            currentPage = FIRST_PAGE,
            searchItemList = persistentListOf(),
            favoriteUrls = persistentSetOf(),
            rawMediaItems = persistentListOf(),
        )

        fun fromSearchResult(
            sduiViewItems: List<SDUIViewTypeVO>?,
            query: String,
            result: MediaSearchResultVO,
            favoriteUrls: ImmutableSet<String>,
        ): SearchUIState = SearchUIState(
            sduiViewItems = sduiViewItems,
            query = query,
            isLoading = false,
            isLoadingMore = false,
            isLastPage = result.isEnd || result.items.isEmpty(),
            hasSearched = true,
            currentPage = FIRST_PAGE,
            searchItemList = result.items.map { it.toUiState(favoriteUrls) }.toImmutableList(),
            favoriteUrls = favoriteUrls,
            rawMediaItems = result.items.toImmutableList(),
        )
    }
}

private fun MediaItemVO.toUiState(favoriteUrls: Set<String>): MediaItemUiState = MediaItemUiState(
    mediaType = type,
    title = title,
    thumbnailUrl = thumbnailImageUrl,
    contentsUrl = urlKey,
    descriptionText = pageLinkUrl,
    collection = collection,
    dateTimeText = formatKakaoDateTime(dateTime),
    page = page,
    isFavorite = urlKey in favoriteUrls,
)

internal fun Set<String>.asImmutable(): ImmutableSet<String> = toImmutableSet()
