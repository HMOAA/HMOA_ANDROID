plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

android {
    namespace = "com.hmoa.feature_hpedia"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
}

dependencies {
    implementation(project(":core-designsystem"))
    implementation(project(":core-domain"))
    implementation(project(":core-model"))
    implementation(project(":core-common"))
    implementation(project(":feature-community"))

    implementation(libs.bundles.ui)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.paging.compose)
    implementation(libs.bundles.basic)
    implementation(libs.google.material)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    testAnnotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.viewmodel)

    debugImplementation(libs.ui.tooling)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}
