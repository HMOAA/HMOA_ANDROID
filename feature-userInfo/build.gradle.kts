import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
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
        buildConfigField("String", "TERMS_OF_SERVICE", localProperties.getProperty("TERMS_OF_SERVICE"))
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
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core-designsystem"))
    implementation(project(":core-domain"))
    implementation(project(":core-model"))
    implementation(project(":core-common"))

    implementation(libs.bundles.ui)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.paging.compose)
    implementation(libs.open.licenses)
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.kakao.login)
    implementation(libs.bundles.basic)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    testAnnotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.viewmodel)

    debugImplementation(libs.ui.tooling)
    testImplementation(libs.junit)
    implementation(libs.junit.ext)

}
