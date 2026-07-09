package com.jongchan.androidarchi.common.domain.sdui

import kotlinx.serialization.Serializable

/**
 * "image" : { "imgUrl", "size", "bgColor" }
 */
@Serializable
data class SDUIImageVO(
    val imgUrl: String,
    val size: SDUISizeVO,
    val bgColor: String,
)

/**
 * "size" : { "width", "height" }
 */
@Serializable
data class SDUISizeVO(
    val width: Int,
    val height: Int,
)

/**
 * "actionIcon" : { "imgUrl", "size", "action" }
 */
@Serializable
data class SDUIActionIconVO(
    val imgUrl: String,
    val size: SDUISizeVO,
    val action: SDUIActionVO,
)

/**
 * "action" : { "navigateLink", "actionType" }
 * actionType: navigate / showToast / showSnackBar ... (확장 가능하므로 String 유지)
 */
@Serializable
data class SDUIActionVO(
    val navigateLink: String = "",
    val actionType: String = ""
)
