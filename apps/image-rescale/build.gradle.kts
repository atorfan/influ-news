
plugins {
    java
    application
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Vavr with Either
    implementation("io.vavr:vavr:0.10.+")

    // Image rescale library
    implementation("net.coobird:thumbnailator:0.4.+")

    // Database
    implementation("org.postgresql:postgresql")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.6.+")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.+")

    // SpringBoot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // SpringBoot Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")

    // Assertions Library
    testImplementation("org.assertj:assertj-core:3.27.+")

    // Testcontainers
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.21.+"))
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.+")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    // Define the main class for the application.
    mainClass = "com.newsnow.platform.imagerescale.ImageRescaleApplication"
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
