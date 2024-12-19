plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.5.0"
}

android {
    namespace = "com.hmoa.core_repository"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-datastore"))
    implementation(project(":core-domain"))

    implementation(libs.bundles.hilt)
    testAnnotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)
}