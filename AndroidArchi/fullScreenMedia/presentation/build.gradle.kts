import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jongchan.androidarchi.fullScreenMedia.presentation"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        // common 모듈이 -Xexplicit-backing-fields(프리릴리스 기능)로 컴파일되므로,
        // 그 메타데이터를 읽으려면 소비 측도 동일 플래그가 필요하다.
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }
}

dependencies {
    implementation(project(":fullScreenMedia:domain"))
    implementation(project(":common:presentation"))

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // View 상세화면: Fragment + ViewPager2 + ConstraintLayout
    // fragment-ktx 가 끌어오는 androidx.activity 1.8.1 을 프로젝트 공통 버전(1.13.0)으로 맞춘다.
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.constraintlayout)

    // Coil 3 (ImageView.load) + 네트워크 페처
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    // Lottie (하트 애니메이션 오버레이, View 버전)
    implementation(libs.lottie)
}
