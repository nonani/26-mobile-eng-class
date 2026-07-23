package com.jongchan.androidarchi.common.domain.sdui

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

/**
 * SDUI content 의 공통 상위 타입. 구현체는 이 파일 안의 `*ContentVO` 들로 닫혀 있으며,
 * 렌더링 측 `when(content)` 이 별도 else 없이 exhaustive 하게 처리되도록 sealed 로 둔다.
 * 새 content 타입 추가 시: 이 파일에 구현체 추가 → [SDUIViewType] 에 매핑 한 줄 추가.
 */
sealed interface SDUIContentVO

/**
 * 클라이언트가 파싱 가능한 SDUI viewType 화이트리스트이자 content 매핑의 단일 소스.
 *
 * - enum 상수 이름 == 서버가 내려주는 `viewType` 문자열.
 * - 각 상수는 해당 [SDUIContentVO] 구현체의 serializer 를 들고 있어, 어떤 타입으로 디코딩할지 결정한다.
 * - 여기 없는 viewType 은 "미지원" 으로 간주되어 파싱 결과 리스트에서 제외된다([from] 가 null 반환).
 *
 * 새 SDUI 컴포넌트 추가 = 이 enum 에 한 줄 추가하는 것으로 끝난다.
 */
enum class SDUIViewType(val contentSerializer: KSerializer<out SDUIContentVO>) {
    TitleDescriptionViewType(TitleDescViewTypeVO.serializer()),
    TitleDescriptionEndImageViewType(TitleDescEndImageViewTypeVO.serializer()),
    StartImageTitleDescriptionActionIconType(StartImageTitleDescActionIconViewTypeVO.serializer()),
    ;

    companion object {
        /** 미지원 viewType(오타/null 포함)이면 null. */
        fun from(viewType: String?): SDUIViewType? =
            entries.find { it.name == viewType }
    }
}

/**
 * 파싱 완료된 SDUI 컴포넌트 1건. data 레이어의 toVO 매퍼에서만 생성된다.
 * (역직렬화는 [content] 타입별 serializer 로 개별 수행하므로 이 클래스 자체는 @Serializable 이 아니다.)
 */
data class SDUIViewTypeVO(
    val viewType: SDUIViewType,
    val content: SDUIContentVO
)

/**
 * viewType == "TitleDescriptionViewType"
 */
@Serializable
data class TitleDescViewTypeVO(
    val titleText: String,
    val descriptionText: String,
) : SDUIContentVO

/**
 * viewType == "TitleDescriptionEndImageViewType"
 */
@Serializable
data class TitleDescEndImageViewTypeVO(
    val titleText: String,
    val descriptionText: String,
    val endImage: SDUIImageVO,
) : SDUIContentVO

/**
 * viewType == "StartImageTitleDescriptionActionIconType"
 */
@Serializable
data class StartImageTitleDescActionIconViewTypeVO(
    val titleText: String,
    val descriptionText: String,
    val image: SDUIImageVO,
    val actionIcon: SDUIActionIconVO,
) : SDUIContentVO
