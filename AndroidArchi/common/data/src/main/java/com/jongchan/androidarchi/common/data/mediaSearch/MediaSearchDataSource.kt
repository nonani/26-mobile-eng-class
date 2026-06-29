package com.jongchan.androidarchi.common.data.mediaSearch

import com.jongchan.androidarchi.common.data.BaseRemoteDataSource
import com.jongchan.androidarchi.common.data.mediaSearch.dto.ImageSearchResponse
import com.jongchan.androidarchi.common.data.mediaSearch.dto.VideoSearchResponse

class MediaSearchDataSource(private val apiService: MediaSearchApiService) : BaseRemoteDataSource() {

    suspend fun searchImages(query: String, page: Int): ImageSearchResponse {
        return checkResponse(apiService.searchImages(query = query, page = page))
    }

    suspend fun searchVideos(query: String, page: Int): VideoSearchResponse {
        return checkResponse(apiService.searchVideos(query = query, page = page))
    }
}
