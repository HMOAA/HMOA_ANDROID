import java.util.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.5.0"
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./feature-authentication/local.properties").inputStream())
}

android {
    namespace = "com.hmoa.feature_authentication"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        manifestPlaceholders["NATIVE_APP_KEY"] = localProperties.getProperty("NATIVE_APP_KEY")
        buildConfigField(
            "String",
            "GOOGLE_CLOUD_CLIENT_SECRET",
            localProperties.getProperty("GOOGLE_CLOUD_CLIENT_SECRET")
        )
        buildConfigField(
            "String",
            "GOOGLE_CLOUD_OAUTH_CLIENT_ID",
            localProperties.getProperty("GOOGLE_CLOUD_OAUTH_CLIENT_ID")
        )
        buildConfigField("String", "REDIRECT_URI", localProperties.getProperty("REDIRECT_URI"))
        buildConfigField("String", "GRANT_TYPE", localProperties.getProperty("GRANT_TYPE"))
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
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")
    val hilt_version = "2.48.1"
    val kakao_version = "2.20.5"
    val hilt_viewmodel_version = "1.0.0-alpha03"
    val hilt_nav_compose_version = "1.0.0"

    implementation(project(":core-designsystem"))
    implementation(project(":core-domain"))
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    implementation("com.kakao.sdk:v2-all:${kakao_version}")// 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:${kakao_version}") // 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-talk:${kakao_version}") // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation("com.kakao.sdk:v2-share:${kakao_version}") // 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-friend:${kakao_version}") // 피커 API 모듈
    implementation("com.kakao.sdk:v2-cert:${kakao_version}") // 카카오 인증서비스 API 모듈
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("com.google.dagger:hilt-compiler:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_nav_compose_version")
    kapt("androidx.hilt:hilt-compiler:$hilt_viewmodel_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.5.1")

    implementation("androidx.compose.material:material:1.2.0-beta02")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //goole-login
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.gms:google-services:4.4.1")
    implementation("com.google.firebase:firebase-bom:32.0.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1-Beta")
}