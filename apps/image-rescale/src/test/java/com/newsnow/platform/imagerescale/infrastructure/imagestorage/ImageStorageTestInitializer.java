package com.newsnow.platform.imagerescale.infrastructure.imagestorage;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

import java.net.URI;
import java.util.Map;

public final class ImageStorageTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String BUCKET_NAME = "test-bucket";

    private static final LocalStackContainer localStackContainer = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:3.4")
    )
            .withServices(LocalStackContainer.Service.S3);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        startContainers();

        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        final MapPropertySource testcontainers = new MapPropertySource("testcontainers", getEnvVariables());
        environment.getPropertySources().addFirst(testcontainers);

        createBucket();
    }

    private static void createBucket() {
        try (S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create(localStackContainer.getEndpointOverride(LocalStackContainer.Service.S3).toString()))
                .region(Region.of(localStackContainer.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(localStackContainer.getAccessKey(), localStackContainer.getSecretKey())))
                .forcePathStyle(true)
                .build()) {

            // Check if bucket exists, create if not
            try {
                s3Client.headBucket(HeadBucketRequest.builder().bucket(BUCKET_NAME).build());
                System.out.println("Bucket " + BUCKET_NAME + " already exists");
            } catch (Exception e) {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(BUCKET_NAME).build());
                System.out.println("Created bucket " + BUCKET_NAME);
            }
        }
    }

    private static void startContainers() {
        Startables.deepStart(localStackContainer).join();
    }

    private static Map<String, Object> getEnvVariables() {
        return Map.of(
                // Spring Cloud AWS properties
                "spring.cloud.aws.region.static", localStackContainer.getRegion(),
                "spring.cloud.aws.credentials.access-key", localStackContainer.getAccessKey(),
                "spring.cloud.aws.credentials.secret-key", localStackContainer.getSecretKey(),
                "spring.cloud.aws.s3.endpoint", localStackContainer.getEndpointOverride(LocalStackContainer.Service.S3).toString(),

                // Application properties
                "app.images.storage.bucket-name", BUCKET_NAME
        );
    }
}
