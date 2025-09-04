package com.newsnow.platform.imagerescale.infrastructure.configuration;

import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.UseCaseResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TEST_IMAGE_PATH;

@Configuration
public class TestConfiguration {

    @Bean
    @ConditionalOnMissingBean
    RescaleImage rescaleImageUseCase() {
        return command -> UseCaseResult.success(TEST_IMAGE_PATH);
    }
}
