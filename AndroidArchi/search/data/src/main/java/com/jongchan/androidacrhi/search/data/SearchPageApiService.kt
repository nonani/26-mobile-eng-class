package com.jongchan.androidacrhi.search.data

import com.jongchan.androidacrhi.search.data.dto.SearchPageDTO
import retrofit2.Response
import retrofit2.http.GET

interface SearchPageApiService {
    @GET("v2/search/page")
    suspend fun getSearchPage(): Response<SearchPageDTO>
}
