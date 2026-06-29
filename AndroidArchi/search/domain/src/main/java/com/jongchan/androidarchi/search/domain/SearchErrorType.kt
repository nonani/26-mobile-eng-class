package com.jongchan.androidarchi.search.domain

import com.jongchan.androidarchi.common.domain.error.HttpErrorType

enum class SearchErrorType(
    override val type: String,
    override val errorMsg: String,
    override val isHandledOnDomain: Boolean = true
) : HttpErrorType {
    UNKNOWN(
        type = "api.search.unknown",
        errorMsg = "알수없는 에러가 발생했습니다.",
        isHandledOnDomain = true
    )
}
