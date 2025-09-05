package com.newsnow.platform.imagerescale.infrastructure.configuration;

import com.newsnow.platform.imagerescale.adapters.driven.persistence.InMemoryRescaleImageTaskRepository;
import com.newsnow.platform.imagerescale.core.FindRescaleImageTaskUseCase;
import com.newsnow.platform.imagerescale.core.ports.api.FindRescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import com.newsnow.platform.imagerescale.core.ports.api.UseCaseResult;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.IMAGE_URL;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTask;

@Configuration
public class TestConfiguration {

    public static final byte[] IMAGE_DATA_ERROR = "imageError".getBytes(StandardCharsets.UTF_8);

    @Bean
    @ConditionalOnMissingBean
    RescaleImage rescaleImageUseCase() {
        return command -> {
            if (isImageError(command)) {
                return UseCaseResult.failure("This is an error");
            }
            return UseCaseResult.success(IMAGE_URL);
        };
    }

    private static boolean isImageError(RescaleImageCommand command) {
        return Arrays.equals(command.imageData(), IMAGE_DATA_ERROR);
    }

    @Bean
    @ConditionalOnMissingBean
    RescaleImageTaskRepository rescaleImageTaskRepository() {
        return new InMemoryRescaleImageTaskRepository(aRescaleImageTask());
    }

    @Bean
    @ConditionalOnMissingBean
    FindRescaleImageTask findRescaleImageTask(RescaleImageTaskRepository rescaleImageTaskRepository) {
        return new FindRescaleImageTaskUseCase(rescaleImageTaskRepository);
    }
}
