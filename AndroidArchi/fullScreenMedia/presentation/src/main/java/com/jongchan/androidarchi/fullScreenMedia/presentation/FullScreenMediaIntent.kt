package com.jongchan.androidarchi.fullScreenMedia.presentation

import com.jongchan.androidarchi.common.entity.media.MediaItemVO
import com.jongchan.androidarchi.common.presentation.mvi.MviIntent

sealed interface FullScreenMediaIntent : MviIntent {
    data class AddFavorite(val item: MediaItemVO) : FullScreenMediaIntent
    data class DeleteFavorite(val url: String) : FullScreenMediaIntent
    data object ClickBackButton : FullScreenMediaIntent

    /** 페이저 스와이프로 현재 페이지가 바뀜. 위치를 상태에 보존해 회전 후에도 유지한다. */
    data class SelectPage(val index: Int) : FullScreenMediaIntent
}
