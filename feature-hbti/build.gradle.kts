plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.hmoa.feature_hbti"
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
            excludes.add("META-INF/gradle/incremental.annotation.processors")
        }
    }

}

dependencies {
    val hilt_version = "2.48.1"
    val hilt_viewmodel_version = "1.0.0-alpha03"
    val hilt_nav_compose_version = "1.0.0"
    val kotlinx_version = "1.5.0"

    implementation(project(":core-designsystem"))
    implementation(project(":core-domain"))
    implementation(project(":core-model"))
    implementation(project(":core-common"))
    implementation(project(":core-repository"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_version")
    implementation("com.google.code.gson:gson:2.9.0")

    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("com.google.dagger:hilt-compiler:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_nav_compose_version")
    kapt("androidx.hilt:hilt-compiler:$hilt_viewmodel_version")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    testImplementation("junit:junit:4.13.2")
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    //unit test
    val mockito_version = "4.8.0"
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    testImplementation("org.mockito:mockito-core:$mockito_version")
    testImplementation("org.mockito:mockito-inline:$mockito_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.30")
    //hilt test
    kaptTest("com.google.dagger:hilt-android-compiler:$hilt_version") // ...with Kotlin.
    testImplementation("com.google.dagger:hilt-android-testing:$hilt_version") // For Robolectric tests.
    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.44") // ...with Java.

    //androidTest
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
    // AndroidX Test 라이브러리 - Android 기기에서 실행되는 테스트를 위한 라이브러리
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //Mockito androidTest
    androidTestImplementation("org.mockito:mockito-core:5.3.1")
    androidTestImplementation("org.mockito:mockito-android:5.3.1")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    //Hilt androidTest
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hilt_version")
}