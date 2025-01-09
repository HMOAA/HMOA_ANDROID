import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
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
        kotlinCompilerExtensionVersion = "1.5.7"
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
    implementation(project(":core-designsystem"))
    implementation(project(":core-domain"))
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.viewmodel)
    testAnnotationProcessor(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.bundles.kakao.login)
    implementation(libs.bundles.google.login)
    implementation(libs.firebase.messaging)
    implementation(libs.bundles.lifecycle)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.basic)
    implementation(libs.google.material)
    implementation(libs.constraintlayout)

    // test
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.coroutine.test)
}
