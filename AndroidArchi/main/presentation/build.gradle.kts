import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.jongchan.androidarchi.main.presentation"
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
        buildConfig = true
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }
}

composeCompiler {
    stabilityConfigurationFiles.add(rootProject.layout.projectDirectory.file("compose_stability.conf"))
    if (providers.gradleProperty("composecompiler.reports").orNull == "true") {
        val outDir = rootProject.layout.buildDirectory.dir(
            "compose_reports/${project.path.replace(":", "_").trim('_')}"
        )
        reportsDestination.set(outDir)
        metricsDestination.set(outDir)
    }
}

dependencies {
    implementation(project(":main:domain"))
    implementation(project(":main:entity"))
    implementation(project(":common:presentation"))

    // Feature dependencies (Assuming Main navigates to these)
    implementation(project(":intro:presentation"))
    implementation(project(":search:presentation"))
    implementation(project(":favorite:presentation"))
    implementation(project(":fullScreenMedia:presentation"))

    // 각 feature 의 *Page (path/Args 정의) 를 호스트 측 라우터에서 직접 참조한다.
    implementation(project(":intro:domain"))
    implementation(project(":search:domain"))
    implementation(project(":favorite:domain"))
    implementation(project(":fullScreenMedia:domain"))

    implementation(libs.androidx.activity.compose)
    // 상세화면 Fragment 호스팅(FragmentActivity, FragmentContainerView).
    implementation(libs.androidx.fragment.ktx)
    api(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
