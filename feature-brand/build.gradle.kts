plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

android {
    namespace = "com.hmoa.feature_brand"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        compilerOptions.freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_metrics",
        )
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        compilerOptions.freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_reports",
        )
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        compilerOptions.freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:experimentalStrongSkipping=true",
        )
    }
}

dependencies {

    implementation(project(":core-designsystem"))
    implementation(project(":core-domain"))
    implementation(project(":core-model"))
    implementation(project(":core-common"))

    implementation(libs.paging.compose)
    implementation(libs.bundles.ui)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.basic)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    testAnnotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.viewmodel)

    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    debugImplementation(libs.ui.tooling)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
}
