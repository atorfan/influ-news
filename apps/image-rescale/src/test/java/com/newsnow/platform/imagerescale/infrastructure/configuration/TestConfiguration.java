package com.newsnow.platform.imagerescale.infrastructure.configuration;

import com.newsnow.platform.imagerescale.core.ports.api.FindRescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.UseCaseResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.CREATED_AT;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.IMAGE_URL;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.ORIGINAL_IMAGE_HASH;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TASK_ID;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.WIDTH;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTaskWith;

@Configuration
public class TestConfiguration {

    @Bean
    @ConditionalOnMissingBean
    RescaleImage rescaleImageUseCase() {
        return command -> UseCaseResult.success(IMAGE_URL);
    }

    @Bean
    @ConditionalOnMissingBean
    FindRescaleImageTask findRescaleImageTask() {
        return ignored -> aRescaleImageTaskWith(
                TASK_ID,
                CREATED_AT,
                ORIGINAL_IMAGE_HASH,
                WIDTH,
                HEIGHT,
                IMAGE_URL
        );
    }
}
