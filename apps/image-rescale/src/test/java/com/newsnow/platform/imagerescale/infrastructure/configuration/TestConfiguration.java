package com.newsnow.platform.imagerescale.infrastructure.configuration;

import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.UseCaseResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    public static final String TEST_IMAGE_PATH = "/images/test.jpg";

    @Bean
    RescaleImage rescaleImageUseCase() {
        return command -> UseCaseResult.success(TEST_IMAGE_PATH);
    }
}
