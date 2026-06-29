package com.jongchan.androidarchi.intro.data

import com.jongchan.androidarchi.intro.domain.IntroRepository

class IntroRepositoryImpl(val dataSource: IntroDataSource) : IntroRepository {
    override suspend fun getIntro() = dataSource.getIntro().toVO()
}
