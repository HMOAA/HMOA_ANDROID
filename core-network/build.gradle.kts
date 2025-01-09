import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.plugin.serialization)
    id("kotlinx-serialization")
    kotlin("kapt")
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./core-network/local.properties").inputStream())
}

android {
    namespace = "com.hmoa.core_network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "LIBRARY_PACKAGE_NAME", "\"com.hmoa.core_network\"")
        buildConfigField("String", "BASE_URL", localProperties.getProperty("BASE_URL"))
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-database"))
    implementation(project(":core-common"))

    implementation(libs.sandwich)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.datastore)
    implementation(libs.bundles.retrofit)

    implementation(libs.bundles.hilt)
    testAnnotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)

    //test
    testImplementation(libs.bundles.unit.test)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}