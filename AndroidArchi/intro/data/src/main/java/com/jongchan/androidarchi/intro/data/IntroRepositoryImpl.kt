package com.jongchan.androidarchi.intro.data

import com.jongchan.androidarchi.intro.domain.IntroRepository
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(
    val dataSource: IntroDataSource,
) : IntroRepository {
    override suspend fun getIntro() = dataSource.getIntro().toVO()
}
