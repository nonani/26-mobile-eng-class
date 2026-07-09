package com.jongchan.androidarchi.common.data.sdui.dto

import com.jongchan.androidarchi.common.domain.sdui.SDUIViewTypeVO
import com.jongchan.androidarchi.common.domain.sdui.SDUIViewType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * SDUI 컴포넌트 1건의 원본(wire) 표현.
 *
 * [content] 는 viewType 이 확정되기 전이라 특정 VO 로 바로 디코딩할 수 없으므로 raw [JsonElement] 로 보류한다.
 * 실제 타입 확정/디코딩은 [toVOorNull] 에서 [SDUIViewType] 매핑을 통해 수행한다.
 */
@Serializable
data class SDUIComponentDTO(
    val viewType: String? = null,
    val content: JsonElement? = null,
)

/**
 * 미지원 viewType(클라이언트에 매핑 클래스 없음) 또는 content 형식 불일치 → null.
 * = "sduiComponents" 리스트에 애초에 포함되지 않는다.
 */
fun SDUIComponentDTO.toVOorNull(json: Json): SDUIViewTypeVO? {
    val type = SDUIViewType.from(viewType) ?: return null
    val element = content ?: return null
    val contentVO = runCatching { json.decodeFromJsonElement(type.contentSerializer, element) }
        .getOrNull() ?: return null
    return SDUIViewTypeVO(viewType = type, content = contentVO)
}

/**
 * 미지원/깨진 항목은 제외하고 파싱 가능한 컴포넌트만 순서 유지하여 반환한다.
 */
fun List<SDUIComponentDTO>?.toSDUIComponents(json: Json): List<SDUIViewTypeVO> =
    this.orEmpty().mapNotNull { it.toVOorNull(json) }
