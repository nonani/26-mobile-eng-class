package com.jongchan.androidarchi.intro.data.dto

import com.jongchan.androidarchi.common.entity.UNKNOWN
import com.jongchan.androidarchi.intro.entity.IntroVO
import kotlinx.serialization.Serializable

@Serializable
data class IntroDTO(
    val devTestMsg: String? = null,
    val minAppVersion: String? = null,
    val recommendAppVersion: String? = null
) {
    fun toVO(): IntroVO {
        return IntroVO(
            devTestMsg = devTestMsg  ?: UNKNOWN,
            minAppVersion = minAppVersion  ?: UNKNOWN,
            recommendAppVersion = recommendAppVersion  ?: UNKNOWN
        )
    }
}
