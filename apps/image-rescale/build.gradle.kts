
plugins {
    java
    application
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
    implementation("org.postgresql:postgresql:42.7.+")

    // SpringBoot
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.5"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.+")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.+")

    // SpringBoot Test
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.5.+")

    // Assertions Library
    testImplementation("org.assertj:assertj-core:3.27.+")

    // Testcontainers
    testImplementation("org.testcontainers:testcontainers:1.21.+")
    testImplementation("org.testcontainers:junit-jupiter:1.21.+")
    testImplementation("org.testcontainers:postgresql:1.21.+")

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
