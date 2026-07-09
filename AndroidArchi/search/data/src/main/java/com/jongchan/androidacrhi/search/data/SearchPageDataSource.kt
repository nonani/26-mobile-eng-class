package com.jongchan.androidacrhi.search.data

import com.jongchan.androidacrhi.search.data.dto.SearchPageDTO
import com.jongchan.androidarchi.common.data.BaseRemoteDataSource
import kotlinx.serialization.json.Json

class SearchPageDataSource(
    private val apiService: SearchPageApiService,
    private val json: Json,
) : BaseRemoteDataSource() {

    suspend fun getSearchPage(): SearchPageDTO {
        // TODO: 서버 `v2/search/page` 미동작 → 목 JSON 파싱으로 대체.
        //  서버 준비되면 아래 실제 호출로 복구하고 SearchPageMockData 를 삭제한다.
        //  return checkResponse(apiService.getSearchPage())
        return json.decodeFromString(SearchPageMockData.SEARCH_PAGE_JSON)
    }
}
