package com.jongchan.androidarchi.intro.entity

data class IntroVO(
    val devTestMsg: String,
    val minAppVersion: String,
    val recommendAppVersion: String
) {
    companion object {
        val empty: IntroVO = IntroVO("", "", "")
    }
}
