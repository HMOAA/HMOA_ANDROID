import java.util.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./feature-authentication/local.properties").inputStream())
}

android {
    namespace = "com.hmoa.feature_authentication"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        manifestPlaceholders["NATIVE_APP_KEY"] = localProperties.getProperty("NATIVE_APP_KEY")
        buildConfigField("String", "NATIVE_APP_KEY", localProperties.getProperty("NATIVE_APP_KEY"))
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    val hilt_version = "2.44"
    val hilt_viewmodel_version = "1.0.0-alpha03"
    val hilt_nav_compose_version = "1.0.0"

    implementation(project(":core-designsystem"))
    implementation(project(":core-repository"))

    implementation("com.kakao.sdk:v2-all:2.19.0")// 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.19.0") // 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-talk:2.19.0") // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation("com.kakao.sdk:v2-share:2.19.0") // 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-friend:2.19.0") // 피커 API 모듈
    implementation("com.kakao.sdk:v2-cert:2.19.0") // 카카오 인증서비스 API 모듈
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("com.google.dagger:hilt-compiler:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_nav_compose_version")
    kapt("androidx.hilt:hilt-compiler:$hilt_viewmodel_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}