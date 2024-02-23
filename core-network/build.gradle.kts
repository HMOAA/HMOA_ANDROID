plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}


dependencies {
    val ktor_version = "2.3.7"
    val mockito_version = "4.8.0"

    implementation(project(":core-model"))
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp-jvm:$ktor_version")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:$mockito_version")
    testImplementation("org.mockito:mockito-inline:$mockito_version")
    implementation(kotlin("stdlib"))
}