plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.hmoa.core_database"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "LIBRARY_PACKAGE_NAME", "\"com.hmoa.core_database\"")
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
    sourceSets {
        // 기존의 test 소스셋을 unitTest로 변경
        getByName("test") {
            java.srcDirs("src/unitTest/java")
        }
    }
}

dependencies {
    val hilt_version = "2.48.1"
    val datastore_version = "1.0.0"
    val room_version = "2.6.1"

    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("androidx.datastore:datastore-preferences:$datastore_version")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

//    testImplementation("junit:junit:4.13.2")
//    testImplementation("androidx.test:core-ktx:1.6.1")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
//    implementation("androidx.test.ext:junit-ktx:1.2.1")
//    kaptTest("com.google.dagger:hilt-android-compiler:$hilt_version") // ...with Kotlin.
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    testImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
//    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.44")

    testImplementation("junit:junit:4.13.2")
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    //unit test
    val mockito_version = "4.8.0"
    testImplementation("junit:junit:4.13.2")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    testImplementation("org.mockito:mockito-core:$mockito_version")
    testImplementation("org.mockito:mockito-inline:$mockito_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.30")
    //hilt test
    kaptTest("com.google.dagger:hilt-android-compiler:$hilt_version") // ...with Kotlin.
    testImplementation("com.google.dagger:hilt-android-testing:$hilt_version") // For Robolectric tests.
    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.44") // ...with Java.
}