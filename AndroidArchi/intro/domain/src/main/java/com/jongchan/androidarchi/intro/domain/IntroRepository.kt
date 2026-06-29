package com.jongchan.androidarchi.intro.domain

import com.jongchan.androidarchi.intro.entity.IntroVO

interface IntroRepository {

    suspend fun getIntro() : IntroVO
}
