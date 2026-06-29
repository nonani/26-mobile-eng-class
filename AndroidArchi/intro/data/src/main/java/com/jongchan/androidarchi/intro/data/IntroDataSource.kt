package com.jongchan.androidarchi.intro.data

import com.jongchan.androidarchi.common.data.BaseRemoteDataSource
import com.jongchan.androidarchi.intro.data.dto.IntroDTO

class IntroDataSource(private val introApiService: IntroApiService) : BaseRemoteDataSource() {
    suspend fun getIntro(): IntroDTO {
        return checkResponse(introApiService.getIntro())
    }
}
