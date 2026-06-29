package com.jongchan.androidarchi.favorite.data.local

import com.jongchan.androidarchi.common.entity.UNKNOWN
import com.jongchan.androidarchi.common.entity.favorite.FavoriteItemVO
import com.jongchan.androidarchi.common.entity.media.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteItemDto(
    val url: String?,
    val type: Int?,
    val title: String?,
    val thumbnailUrl: String?,
    // 이전 버전 저장본엔 없을 수 있어 기본값 null. (이미지=원본, 동영상=썸네일)
    val contentsImageUrl: String? = null,
    val dateTime: String?,
)

fun FavoriteItemDto.toVO(): FavoriteItemVO = FavoriteItemVO(
    type = MediaType.fromRawValue(type),
    title = title ?: UNKNOWN,
    urlKey = url.orEmpty(),
    thumbnailUrl = thumbnailUrl.orEmpty(),
    contentsImageUrl = contentsImageUrl.orEmpty(),
    dateTime = dateTime.orEmpty(),
)

fun FavoriteItemVO.toDto(): FavoriteItemDto = FavoriteItemDto(
    url = urlKey,
    type = type.rawValue,
    title = title,
    thumbnailUrl = thumbnailUrl,
    contentsImageUrl = contentsImageUrl,
    dateTime = dateTime,
)
