package com.jongchan.androidarchi.fullScreenMedia.domain.tti

import com.jongchan.androidarchi.tti.TTIPage
import com.jongchan.androidarchi.tti.TimelineCategory

object FullScreenMediaTTIPage : TTIPage {
    override val pageName = "fullScreenMediaPage"
    override val timelines = listOf(
        TimelineCategory.TTI_TIME,
        TimelineCategory.API_REQUEST_READY_TIME,
        TimelineCategory.API_RESPONSE_TIME,
    )
}