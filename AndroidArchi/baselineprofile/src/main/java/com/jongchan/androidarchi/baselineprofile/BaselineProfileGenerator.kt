package com.jongchan.androidarchi.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 앱의 대표적인 사용자 시나리오를 실제 단말에서 실행하면서 ART 가
 * "자주 호출된" 클래스/메서드를 dump 하도록 한다.
 *
 * 기록하는 시나리오 (검색 → 결과 스크롤 → 상세 진입)
 *   1. 콜드 스타트. 인트로가 즉시 SearchPage 로 전환되며 검색창이 뜬다.
 *   2. 검색창에 "kakao" 입력 후 IME 검색 액션(가상 키보드 검색 버튼)으로 검색 실행.
 *   3. 검색 결과 리스트를 아래로 3회 fling 하고, 화면에 보이는 아이템을 클릭.
 *   4. FullScreenMediaFragment 의 이미지 페이저가 보일 때까지(로딩 완료) 대기.
 *
 * 실행 방법
 *   - 연결된 기기:  ./gradlew :app:generateBaselineProfile
 *   - GMD 사용:     ./gradlew :app:generateBaselineProfile -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader
 *
 * 산출물 위치
 *   app/src/release/generated/baselineProfiles/baseline-prof.txt
 *
 * 주의: 검색·썸네일 로딩이 네트워크에 의존하므로 단말/GMD 에 네트워크가 있어야 안정적으로 기록된다.
 */
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(
        packageName = PACKAGE_NAME,
        includeInStartupProfile = true,
    ) {
        // 1. 콜드 스타트 → 인트로가 즉시 SearchPage 로 전환됨. 검색 입력창이 뜰 때까지 대기.
        //    Compose 의 testTagsAsResourceId 는 testTag 문자열을 "그대로"(패키지 접두어 없이)
        //    resource-id 로 노출하므로, 단일 인자 By.res(tag) 로 찾아야 한다.
        //    (2-인자 By.res(pkg, tag) 는 `pkg:id/tag` 패턴이라 Compose testTag 와 매칭되지 않는다.)
        startActivityAndWait()
        device.wait(Until.hasObject(By.res(SEARCH_FIELD_TEST_TAG)), UI_TIMEOUT_MS)

        // 2. 검색창을 포커스(클릭)한 뒤 "kakao" 입력 → ENTER 로 IME 검색 액션 실행.
        //    setText 는 접근성 액션으로 onValueChange 를 트리거하고, singleLine 필드라
        //    pressEnter(ENTER) 가 ImeAction.Search(onSearch) 로 연결된다. 키 이벤트가 입력창에
        //    전달되도록 먼저 click 으로 포커스를 준다.
        val searchField = device.findObject(By.res(SEARCH_FIELD_TEST_TAG))
        searchField.click()
        device.waitForIdle()
        searchField.text = SEARCH_KEYWORD
        device.pressEnter()

        // 3. 검색 결과 셀이 컴포지션될 때까지 대기.
        device.wait(Until.hasObject(By.res(SEARCH_ITEM_TEST_TAG)), SEARCH_TIMEOUT_MS)

        // 4. 결과 리스트를 아래로 3회 fling → list/lazy column 코드 경로를 핫 메서드로 확보.
        device.findObject(By.scrollable(true))?.let { scrollable ->
            scrollable.setGestureMargin(device.displayWidth / SCROLL_MARGIN_DIVISOR)
            repeat(SCROLL_COUNT) {
                scrollable.fling(Direction.DOWN)
                device.waitForIdle()
            }
        }

        // 5. 현재 화면에 보이는 아이템 하나를 클릭 → FullScreenMediaFragment 진입.
        device.findObjects(By.res(SEARCH_ITEM_TEST_TAG)).firstOrNull()?.click()

        // 6. 상세화면의 이미지 페이저가 보일 때까지(로딩 완료) 대기.
        //    mediaPager 는 XML 레이아웃의 실제 View id 라 패키지 접두어가 붙는다 → 2-인자 By.res 사용.
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, MEDIA_PAGER_ID)), DETAIL_TIMEOUT_MS)
    }

    companion object {
        private const val PACKAGE_NAME = "com.jongchan.androidarchi"
        private const val SEARCH_KEYWORD = "kakao"
        private const val SCROLL_COUNT = 3
        private const val SCROLL_MARGIN_DIVISOR = 5

        private const val UI_TIMEOUT_MS = 10_000L
        // 검색/썸네일 로딩은 네트워크 응답을 기다려야 하므로 여유 있게 잡는다.
        private const val SEARCH_TIMEOUT_MS = 15_000L
        private const val DETAIL_TIMEOUT_MS = 15_000L

        // baselineprofile 모듈은 com.android.test 라 다른 모듈을 의존할 수 없어 상수를 복제한다.
        // common:presentation 의 [SEARCH_TEXT_FIELD_TEST_TAG] / [FEED_ITEM_TEST_TAG] 와 같은 값.
        private const val SEARCH_FIELD_TEST_TAG = "search_text_field"
        private const val SEARCH_ITEM_TEST_TAG = "search_item"
        // fullScreenMedia 레이아웃의 view id(R.id.mediaPager) 와 같은 값.
        private const val MEDIA_PAGER_ID = "mediaPager"
    }
}
