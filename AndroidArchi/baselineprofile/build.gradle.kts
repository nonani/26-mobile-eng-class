import com.android.build.api.dsl.ManagedVirtualDevice
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.androidx.baselineprofile)
}

android {
    namespace = "com.jongchan.androidarchi.baselineprofile"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        // Macrobenchmark / Baseline Profile 수집은 API 28+ 단말에서만 동작한다.
        minSdk = libs.versions.minSdkBaselineProfile.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // 측정 대상 앱.
    targetProjectPath = ":app"

    // CI 또는 로컬에서 실 단말 없이 baseline profile 을 만들기 위한 Gradle Managed Device.
    // 연결된 기기로 돌리고 싶다면 그냥 `./gradlew :app:generateBaselineProfile` 만 호출해도 된다.
    @Suppress("UnstableApiUsage")
    testOptions.managedDevices.allDevices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = 34
            systemImageSource = "aosp"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

baselineProfile {
    // GMD 와 연결된 단말 둘 다 허용 — 둘 중 사용 가능한 쪽이 자동 선택된다.
    managedDevices += "pixel6Api34"
    useConnectedDevices = true
}

dependencies {
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(libs.androidx.junit)
}
