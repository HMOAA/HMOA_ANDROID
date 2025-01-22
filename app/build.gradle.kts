import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    kotlin("kapt")
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./app/local.properties").inputStream())
}

android {
    namespace = "com.hmoa.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hmoa.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 37
        versionName = "1.3.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["REDIRECTION_PATH"] = localProperties["REDIRECTION_PATH"] as String
        buildConfigField("String", "NATIVE_APP_KEY", localProperties["NATIVE_APP_KEY"] as String)
        buildConfigField("String", "BOOTPAY_APPLICATION_ID", localProperties["BOOTPAY_APPLICATION_ID"] as String)
    }

    signingConfigs {
        create("release") {
            keyAlias = localProperties.getProperty("KEY_ALIAS")
            keyPassword = localProperties.getProperty("KEY_PASSWORD")
            storeFile = file("./release.keystore")
            storePassword = localProperties.getProperty("STORE_PASSWORD")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }

    applicationVariants.all {
        this.mergeResourcesProvider.configure {
            doLast {
                copy {
                    from(":HMOA_ANDROID_SECRET")
                }
            }
        }
    }

}

dependencies {
    implementation(project(":feature-home"))
    implementation(project(":feature-authentication"))
    implementation(project(":feature-userInfo"))
    implementation(project(":feature-community"))
    implementation(project(":feature-perfume"))
    implementation(project(":feature-brand"))
    implementation(project(":feature-hpedia"))
    implementation(project(":feature-fcm"))
    implementation(project(":feature-magazine"))
    implementation(project(":feature-hbti"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-model"))
    implementation(project(":core-domain"))
    implementation(project(":core-repository"))
    implementation(project(":core-common"))

    implementation(libs.app.update.ktx)
    implementation(libs.bootpay) //부트페이
    implementation(libs.bundles.kakao.login) // kakao
    implementation(libs.bundles.hilt) // hilt
    kapt(libs.hilt.android.compiler)
    testAnnotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.viewmodel)
    implementation(libs.bundles.ui)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.basic)
    implementation(libs.google.material)
    implementation(libs.splash.screen) // splash
    implementation(libs.bundles.firebase) // firebase
    implementation(libs.bundles.google.login) //google
    implementation(libs.open.licenses)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}
