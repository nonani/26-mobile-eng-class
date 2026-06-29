package com.jongchan.androidarchi.main.presentation.navigation

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit

/**
 * Compose Navigation3 destination 자리에서 안드로이드 [Fragment] 를 호스팅한다.
 *
 * 상세화면만 XML/ViewBinding 으로 구현되므로(과제 스펙), 나머지 Compose 네비게이션은
 * 그대로 두고 이 한 화면만 [FragmentContainerView] 위에 Fragment 로 띄운다. 백스택/딥링크/
 * 즐겨찾기 동기화 등 기존 네비게이션 동작은 그대로 유지된다.
 *
 * - 컨테이너 id/tag 는 [rememberSaveable] 로 보존해 구성 변경(회전) 시 FragmentManager 가
 *   동일 컨테이너로 Fragment 를 복원하도록 한다.
 * - destination 이 백스택에서 빠져 컴포지션이 dispose 되면 Fragment 도 제거한다.
 *   단, 구성 변경(상태 저장됨)으로 인한 dispose 에서는 제거하지 않아 상태를 보존한다.
 *
 * 회전(구성 변경)으로 Activity 가 재생성되면, 이 [FragmentContainerView] 는 Compose 컴포지션
 * 시점(= onCreate 이후)에야 만들어지므로 FragmentManager 가 복원한 Fragment 뷰가 attach 될
 * 컨테이너를 찾지 못해 흰 화면이 된다. 이를 정공법으로 재부착하기는 타이밍 의존성이 커서,
 * [MainActivity] 에 `android:configChanges` 를 선언해 회전 시 Activity 재생성 자체를 막는다.
 * 그러면 Fragment·ViewPager2·ViewModel 이 파괴되지 않아 상태가 그대로 유지된다.
 */
@Composable
fun FragmentHostContainer(
    fragmentClass: Class<out Fragment>,
    arguments: Bundle,
    modifier: Modifier = Modifier,
) {
    val fragmentManager = LocalContext.current.findFragmentActivity().supportFragmentManager
    val containerId = rememberSaveable { View.generateViewId() }
    val fragmentTag = rememberSaveable { "fragment-host-$containerId" }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            FragmentContainerView(context).apply { id = containerId }
        },
        update = {
            if (!fragmentManager.isStateSaved &&
                fragmentManager.findFragmentByTag(fragmentTag) == null
            ) {
                fragmentManager.commit {
                    setReorderingAllowed(true)
                    add(containerId, fragmentClass, arguments, fragmentTag)
                }
            }
        },
    )

    DisposableEffect(fragmentManager, fragmentTag) {
        onDispose {
            if (fragmentManager.isStateSaved) return@onDispose
            val fragment = fragmentManager.findFragmentByTag(fragmentTag) ?: return@onDispose
            fragmentManager.commit(allowStateLoss = true) { remove(fragment) }
        }
    }
}

private tailrec fun Context.findFragmentActivity(): FragmentActivity = when (this) {
    is FragmentActivity -> this
    is ContextWrapper -> baseContext.findFragmentActivity()
    else -> error("Hosting a Fragment requires a FragmentActivity context, but was $this")
}
