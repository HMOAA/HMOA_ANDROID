plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.0"
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
    val kotlinx_version = "1.5.0"
    val kotlinx_collections_immutable_version = "0.3.8"
    
    implementation("io.ktor:ktor-client-serialization:2.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_version")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    implementation(kotlin("stdlib"))
    compileOnly("com.github.skydoves:compose-stable-marker:1.0.5")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinx_collections_immutable_version")
}
