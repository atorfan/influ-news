package com.newsnow.platform.imagerescale.adapters.driven.imagestorage;

import com.newsnow.platform.imagerescale.core.ports.spi.ImageStorage;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.Duration;

@Component
final class AwsImageStorage implements ImageStorage {

    private final S3Template s3Template;
    private final String bucketName;

    AwsImageStorage(
            S3Template s3Template,
            @Value("${app.images.storage.bucket-name}") String bucketName
    ) {
        this.s3Template = s3Template;
        this.bucketName = bucketName;
    }

    @Override
    public String store(String filename, byte[] imageData) {
        S3Resource s3Resource = this.s3Template.upload(bucketName, filename, new ByteArrayInputStream(imageData));
        URL signedGetURL = s3Template.createSignedGetURL(bucketName, filename, Duration.ofDays(1));
        return signedGetURL.toString();
    }
}
