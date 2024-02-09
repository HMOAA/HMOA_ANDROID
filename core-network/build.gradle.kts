plugins {
    kotlin("kapt")
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    implementation(project(":core-model"))
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("com.google.dagger:hilt-compiler:2.44")
    implementation("io.ktor:ktor-client-serialization:2.3.7")
    implementation("io.ktor:ktor-client-logging-jvm:2.3.7")
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    implementation("io.ktor:ktor-client-okhttp-jvm:2.3.7")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    testImplementation("junit:junit:4.13.2")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:2.44")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito:mockito-inline:4.8.0")
}