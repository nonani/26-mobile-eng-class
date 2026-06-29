package com.jongchan.androidarchi.fullScreenMedia.domain

/**
 * FullScreen 화면 진입 경로. 진입 방식에 따라 화면이 미디어 리스트를 구성하는 방법이 달라진다.
 * - [SEARCH]   : 검색 결과는 영속화되지 않으므로 선택한 단일 항목만 인자로 받아 고정 노출(스와이프 불가, 과제 스펙).
 * - [FAVORITE] : 즐겨찾기는 로컬에 영속화돼 있어 진입 시 전체 목록을 재조회한다. 좌우 스와이프 페이징 허용.
 * - [DEEP_LINK]: 외부 진입. url/title 만 받아 단일 항목으로 노출한다(타입 미지정 시 UNKNOWN, 상세는 이미지로 표시).
 */
enum class FullScreenMediaOrigin {
    SEARCH,
    FAVORITE,
    DEEP_LINK,
}
