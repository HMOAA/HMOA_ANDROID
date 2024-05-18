import java.util.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./feature-userInfo/local.properties").inputStream())
}

android {
    namespace = "com.hmoa.feature_userinfo"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KAKAO_CHAT_PROFILE", localProperties.getProperty("KAKAO_CHAT_PROFILE"))
        buildConfigField("String", "PRIVACY_POLICY_URI", localProperties.getProperty("PRIVACY_POLICY_URI"))
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
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    val hilt_version = "2.48.1"
    val hilt_viewmodel_version = "1.0.0-alpha03"
    val hilt_nav_compose_version = "1.0.0"

    implementation(project(":core-designsystem"))
    implementation(project(":core-domain"))
    implementation(project(":core-model"))
    implementation(project(":core-common"))

    //material3
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.material:material:1.2.0-beta02")

    //preview
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.compose.ui:ui:1.1.0")

    //bottom navigation
    implementation("androidx.navigation:navigation-compose:2.7.0")

    //collectAsStateWithLifecycle 함수
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")

    //paging
    implementation("androidx.paging:paging-compose:3.2.0")

    //open licence
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")

    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("com.google.dagger:hilt-compiler:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_nav_compose_version")
    kapt("androidx.hilt:hilt-compiler:$hilt_viewmodel_version")

    implementation("com.kakao.sdk:v2-all:2.19.0")// 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.19.0") // 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-talk:2.19.0") // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation("com.kakao.sdk:v2-share:2.19.0") // 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-friend:2.19.0") // 피커 API 모듈
    implementation("com.kakao.sdk:v2-cert:2.19.0") // 카카오 인증서비스 API 모듈

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
}