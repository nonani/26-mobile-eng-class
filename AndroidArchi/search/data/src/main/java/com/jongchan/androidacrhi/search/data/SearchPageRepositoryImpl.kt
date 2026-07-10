package com.jongchan.androidacrhi.search.data

import com.jongchan.androidacrhi.search.data.dto.toVO
import com.jongchan.androidarchi.search.domain.SearchPageRepository
import com.jongchan.androidarchi.search.domain.SearchPageVO
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SearchPageRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchPageDataSource,
    private val json: Json,
) : SearchPageRepository {
    override suspend fun getSearchPage(): SearchPageVO = searchDataSource.getSearchPage().toVO(json)
}