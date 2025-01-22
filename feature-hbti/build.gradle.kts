import java.util.*

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlinx-serialization")
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./feature-hbti/local.properties").inputStream())
}

android {
    namespace = "com.hmoa.feature_hbti"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "PRIVACY_CONSENT_URL", localProperties.getProperty("PRIVACY_CONSENT_URL"))
        buildConfigField("String", "ADDRESS_SEARCH_URL", localProperties.getProperty("ADDRESS_SEARCH_URL"))
        buildConfigField(
            "String",
            "BOOTPAY_APPLICATION_ID",
            localProperties.getProperty("BOOTPAY_APPLICATION_ID") as String
        )
        buildConfigField("String", "SHIPPING_REFUND_URL", localProperties.getProperty("SHIPPING_REFUND_URL") as String)
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
        buildConfig = true
    }
    packaging {
        resources {
            excludes.add("META-INF/gradle/incremental.annotation.processors")
        }
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
    implementation(project(":core-repository"))

    implementation(libs.bootpay)
    implementation(libs.paging.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.bundles.ui)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.basic)
    implementation(libs.google.material)
    implementation(libs.core.gson)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    testAnnotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.compiler)

    implementation(libs.junit.ktx)
    debugImplementation(libs.ui.tooling)
    testImplementation(libs.bundles.unit.test)
    kaptTest(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.test)

    //androidTest
    androidTestImplementation(libs.ui.test.junit4)
//    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
    debugImplementation(libs.ui.test.manifest)
    // AndroidX Test 라이브러리 - Android 기기에서 실행되는 테스트를 위한 라이브러리
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    testImplementation(libs.bundles.android.test.mockito) //Mockito androidTest
    androidTestImplementation(libs.hilt.android.test) //Hilt androidTest
    kaptAndroidTest(libs.hilt.android.compiler) // ...with Kotlin.
    debugImplementation(libs.ui.tooling)

    //androidTest
    androidTestImplementation(libs.bundles.android.test.mockito) //Mockito androidTest
}
