package com.jongchan.androidarchi.search.domain

interface SearchPageRepository {
    suspend fun getSearchPage(): SearchPageVO
}