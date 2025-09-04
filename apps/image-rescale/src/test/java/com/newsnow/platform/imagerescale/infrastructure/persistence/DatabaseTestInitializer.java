package com.newsnow.platform.imagerescale.infrastructure.persistence;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

public class DatabaseTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("public.ecr.aws/docker/library/postgres:17-alpine")
                    .asCompatibleSubstituteFor("postgres"));

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        startContainers();
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        final MapPropertySource testcontainers = new MapPropertySource("testcontainers", getEnvVariables());
        environment.getPropertySources().addFirst(testcontainers);
    }

    private static void startContainers() {
        Startables.deepStart(postgresContainer).join();
    }

    private static Map<String, Object> getEnvVariables() {
        return Map.of(
                "SECRET_DB_URL", postgresContainer.getJdbcUrl(),
                "SECRET_DB_USERNAME", postgresContainer.getUsername(),
                "SECRET_DB_PASSWORD", postgresContainer.getPassword()
        );
    }
}
