package com.jongchan.androidarchi.baselineprofile

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Baseline Profile 적용 전/후 콜드 스타트 시간 비교용 벤치마크.
 *
 * 실행:
 *   ./gradlew :baselineprofile:connectedBenchmarkAndroidTest
 *
 * 결과는 logcat / build/outputs 의 json 으로 확인.
 */
@RunWith(AndroidJUnit4::class)
class StartupBenchmarks {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() = startup(CompilationMode.None())

    @Test
    fun startupCompilationBaselineProfile() =
        startup(CompilationMode.Partial(baselineProfileMode = BaselineProfileMode.Require))

    private fun startup(compilationMode: CompilationMode) = rule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(StartupTimingMetric()),
        compilationMode = compilationMode,
        startupMode = StartupMode.COLD,
        iterations = ITERATIONS,
        setupBlock = {
            pressHome()
        },
    ) {
        startActivityAndWait()
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), DEFAULT_TIMEOUT_MS)
    }

    companion object {
        private const val PACKAGE_NAME = "com.jongchan.androidarchi"
        private const val ITERATIONS = 10
        private const val DEFAULT_TIMEOUT_MS = 5_000L
    }
}
