package com.jongchan.androidarchi.intro.domain

import com.jongchan.androidarchi.common.domain.navigation.NavRoute
import com.jongchan.androidarchi.common.domain.navigation.Page

object IntroPage : Page {
    const val PATH = ""

    override fun toRoute(): NavRoute = NavRoute(PATH)
}
