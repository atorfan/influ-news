package com.newsnow.platform.imagerescale.adapters.driven.imagestorage;

import com.newsnow.platform.imagerescale.infrastructure.configuration.ImageStorageIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static helpers.ImageContentMother.anImage;
import static org.assertj.core.api.Assertions.assertThat;

@ImageStorageIntegrationTest
class AwsImageStorageShould {

    @Autowired
    private AwsImageStorage imageStorage;

    @Test
    void store_the_image_and_return_signed_url() throws IOException {
        var filename = "image.png";

        var signedUrl = imageStorage.store(filename, anImage());

        assertThat(signedUrl)
                .isNotNull()
                .contains("test-bucket")
                .contains(filename)
                .startsWith("http");
    }

    // TODO test exceptional cases when upload fails, etc.
}