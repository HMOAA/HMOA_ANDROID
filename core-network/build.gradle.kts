plugins {
    kotlin("kapt")
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    val nav_version = "2.5.3"
    val ktor_version = "2.3.7"
    val hilt_version = "2.44"
    val mockito_version = "4.8.0"

    implementation(project(":core-model"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp-jvm:$ktor_version")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    testImplementation("junit:junit:4.13.2")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hilt_version")
    testImplementation("org.mockito:mockito-core:$mockito_version")
    testImplementation("org.mockito:mockito-inline:$mockito_version")
}