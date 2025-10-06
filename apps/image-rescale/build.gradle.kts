
plugins {
    java
    application
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "11.13.+"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:1.21.+")
        mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:3.4.+")
    }
}

dependencies {
    // Vavr with Either
    implementation("io.vavr:vavr:0.10.+")

    implementation("net.logstash.logback:logstash-logback-encoder:8.1")

    // Image rescale library
    implementation("net.coobird:thumbnailator:0.4.+")

    // Database
    implementation("org.postgresql:postgresql")

    // Flyway for Database migrations
    implementation("org.flywaydb:flyway-database-postgresql")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.6.+")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.+")

    // SpringBoot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

    // Spring + AWS S3
    implementation("io.awspring.cloud:spring-cloud-aws-autoconfigure")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")
    testImplementation("io.awspring.cloud:spring-cloud-aws-testcontainers")

    // SpringBoot Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Assertions Library
    testImplementation("org.assertj:assertj-core:3.27.+")

    // Testcontainers
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:localstack")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.+")
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
