package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.adapters.driven.InMemoryRescaleImageTaskRepository;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import com.newsnow.platform.imagerescale.core.ports.spi.NewsNowClock;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.IMAGE_HASH;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TASK_CREATED_AT;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TASK_ID;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTaskWith;
import static helpers.ImageCreationHelperForTest.createImageWithSize;
import static org.assertj.core.api.Assertions.assertThat;

final class RescaleImageUseCaseShould {

    private RescaleImageTaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRescaleImageTaskRepository();
    }

    @Test
    @DisplayName("resizes the image to the desired resolution")
    void resize_image() throws Exception {
        var desiredWidth = 400;
        var desiredHeight = 300;
        var command = aCommandWithDesiredResolution(desiredWidth, desiredHeight);

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWith(desiredWidth, desiredHeight)
                );
    }

    @ParameterizedTest
    @CsvSource({
            "0, 300",
            "400, 0",
            "400, -300",
            "-400, 300"
    })
    @DisplayName("Cannot resize the image because the desired resolution must contain positive values")
    void not_resize_image(int desiredWidth, int desiredHeight) throws Exception {
        var command = aCommandWithDesiredResolution(desiredWidth, desiredHeight);

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isTrue();
        assertThat(repository.findBy(command.id()))
                .isNull();
    }

    @Test
    @DisplayName("Saves the task with the current timestamp as the creation time")
    void save_task_with_created_at() throws Exception {
        var command = aCommand();
        var expectedTimestamp = LocalDateTime.now();

        useCaseWith(() -> expectedTimestamp)
                .apply(command);

        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWith(expectedTimestamp)
                );
    }

    @Test
    @DisplayName("Saves the task with the hash of the content of the original image")
    void save_task_with_original_image_hash() throws Exception {
        var command = aCommand();

        useCase()
                .apply(command);

        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWith(IMAGE_HASH)
                );
    }

    private RescaleImageCommand aCommand() throws IOException {
        return aCommandWithDesiredResolution(400, 300);
    }

    private RescaleImageCommand aCommandWithDesiredResolution(int desiredWidth, int desiredHeight) throws IOException {
        return new RescaleImageCommand(TASK_ID, anImage(), desiredWidth, desiredHeight);
    }

    private static byte[] anImage() throws IOException {
        return createImageWithSize(800, 600);
    }

    private RescaleImage useCase() {
        return new RescaleImageUseCase(repository, () -> TASK_CREATED_AT);
    }

    private RescaleImage useCaseWith(NewsNowClock newsNowClock) {
        return new RescaleImageUseCase(repository, newsNowClock);
    }
}
