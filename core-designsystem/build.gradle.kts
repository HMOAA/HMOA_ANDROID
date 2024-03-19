plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.hmoa.core_designsystem"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //material3
    implementation("androidx.compose.material3:material3:1.1.0")

    //preview
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.compose.ui:ui:1.1.0")
    
    //bottom navigation
    implementation("androidx.navigation:navigation-compose:2.7.0")

    //coil 라이브러리
    implementation("com.github.skydoves:landscapist-glide:1.4.7")
<<<<<<< HEAD

    //paging 라이브러리
    implementation("com.google.accompanist:accompanist-pager:0.20.0")

=======
    implementation("com.github.bumptech.glide:glide:4.16.0")
>>>>>>> da5b8e518156790f9d4d2ca0c8783d515268dbb8
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
}