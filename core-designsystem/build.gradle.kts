plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.hmoa.core_designsystem"
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    implementation(libs.bundles.ui)
    implementation(libs.navigation.compose)

    implementation(libs.bundles.glide) //glide
    implementation(libs.bundles.coil) //coil

    //paging 라이브러리
    implementation(libs.accommpanist.pager)

    implementation(libs.bundles.basic)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    debugImplementation(libs.ui.tooling)
}
