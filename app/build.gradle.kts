import java.util.*

plugins {
    id("com.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./app/local.properties").inputStream())
}

android {
    namespace = "com.hmoa.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hmoa.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 31
        versionName = "1.1.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["REDIRECTION_PATH"] = localProperties["REDIRECTION_PATH"] as String
        buildConfigField("String", "NATIVE_APP_KEY", localProperties["NATIVE_APP_KEY"] as String)
        buildConfigField("String", "BOOTPAY_APPLICATION_ID", localProperties["BOOTPAY_APPLICATION_ID"] as String)
    }

    signingConfigs {
        create("release") {
            keyAlias = localProperties.getProperty("KEY_ALIAS")
            keyPassword = localProperties.getProperty("KEY_PASSWORD")
            storeFile = file("./release.keystore")
            storePassword = localProperties.getProperty("STORE_PASSWORD")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}



dependencies {
    val hilt_version = "2.48.1"
    val hilt_viewmodel_version = "1.0.0-alpha03"
    val hilt_nav_compose_version = "1.0.0"
    val kakao_version = "2.20.5"

    implementation(project(":feature-home"))
    implementation(project(":feature-authentication"))
    implementation(project(":feature-userInfo"))
    implementation(project(":feature-community"))
    implementation(project(":feature-perfume"))
    implementation(project(":feature-brand"))
    implementation(project(":feature-hpedia"))
    implementation(project(":feature-fcm"))
    implementation(project(":feature-magazine"))
    implementation(project(":feature-hbti"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-model"))
    implementation(project(":core-domain"))
    implementation(project(":core-repository"))
    implementation(project(":core-common"))

    implementation("io.github.bootpay:android:4.4.0") //boot pay

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
    implementation("androidx.compose.material:material:1.2.0-beta02")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.core:core-splashscreen:1.2.0-alpha01")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-messaging:21.1.0")
    implementation("com.google.firebase:firebase-analytics")
    //google login
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.gms:google-services:4.4.1")
    //open licence
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
