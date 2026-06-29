package com.jongchan.androidarchi.favorite.domain

import com.jongchan.androidarchi.common.domain.navigation.NavRoute
import com.jongchan.androidarchi.common.domain.navigation.Page

object FavoritePage : Page {
    const val PATH = "/favorite"

    override fun toRoute(): NavRoute = NavRoute(PATH)
}
