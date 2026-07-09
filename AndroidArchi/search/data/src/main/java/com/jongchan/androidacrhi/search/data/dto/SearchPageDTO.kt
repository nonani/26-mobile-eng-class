package com.jongchan.androidacrhi.search.data.dto

import com.jongchan.androidarchi.common.data.sdui.dto.SDUIComponentDTO
import com.jongchan.androidarchi.common.data.sdui.dto.toSDUIComponents
import com.jongchan.androidarchi.search.domain.SearchPageVO
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SearchPageDTO(
    val sduiComponents: List<SDUIComponentDTO>? = null,
)

fun SearchPageDTO.toVO(json: Json): SearchPageVO = SearchPageVO(
    sduiComponents = sduiComponents.toSDUIComponents(json),
)
