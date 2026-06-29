package com.jongchan.androidarchi.search.domain

import com.jongchan.androidarchi.common.domain.navigation.NavRoute
import com.jongchan.androidarchi.common.domain.navigation.Page

object SearchPage : Page {
    const val PATH = "/search"

    override fun toRoute(): NavRoute = NavRoute(PATH)
}
