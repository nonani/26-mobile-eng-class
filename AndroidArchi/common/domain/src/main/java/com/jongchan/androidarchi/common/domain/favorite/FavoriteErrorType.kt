package com.jongchan.androidarchi.common.domain.favorite

import com.jongchan.androidarchi.common.domain.error.HttpErrorType

enum class FavoriteErrorType(
    override val type: String,
    override val errorMsg: String,
    override val isHandledOnDomain: Boolean = true
) : HttpErrorType {
    UNKNOWN(
        type = "api.favorite.unknown",
        errorMsg = "알수없는 에러가 발생했습니다.",
        isHandledOnDomain = true
    )
}