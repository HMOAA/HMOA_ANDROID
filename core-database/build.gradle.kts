plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
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
    implementation(project(":core-model"))

    //room
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.bundles.hilt) //hilt
    implementation(libs.datastore) //data store
    implementation(libs.bundles.basic)

    //unit test
    implementation(libs.junit.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.bundles.unit.test)
    //hilt test
    kaptTest(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.test) // For Robolectric
    testAnnotationProcessor(libs.hilt.android.compiler)
}