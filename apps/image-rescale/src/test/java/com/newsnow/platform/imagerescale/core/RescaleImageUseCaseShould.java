package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.adapters.driven.InMemoryRescaleImageTaskRepository;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import com.newsnow.platform.imagerescale.core.ports.spi.ImageStorage;
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
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.IMAGE_URL;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TASK_CREATED_AT;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TASK_ID;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTaskWithCreationTime;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTaskWithImageHash;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTaskWithImageUrl;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTaskWithResolution;
import static helpers.ImageCreationHelperForTest.createImageWithSize;
import static org.assertj.core.api.Assertions.assertThat;

final class RescaleImageUseCaseShould {

    private RescaleImageTaskRepository repository;
    private NewsNowClock newsNowsClock;
    private ImageStorage imageStorage;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRescaleImageTaskRepository();
        newsNowsClock = () -> TASK_CREATED_AT;
        imageStorage = (filename, imageData) -> IMAGE_URL;
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
        var command = aCommandWith(desiredWidth, desiredHeight);

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isTrue();
        assertThat(repository.findBy(command.id()))
                .isNull();
    }

    @Test
    @DisplayName("Saves the task with the desired resolution")
    void save_task_with_desired_resolution() throws Exception {
        var desiredWidth = 400;
        var desiredHeight = 300;
        var command = aCommandWith(desiredWidth, desiredHeight);

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWithResolution(desiredWidth, desiredHeight)
                );
    }

    @Test
    @DisplayName("Saves the task with the current timestamp as the creation time")
    void save_task_with_created_at() throws Exception {
        var command = aCommand();
        var expectedTimestamp = LocalDateTime.now();

        var result = useCaseWith(() -> expectedTimestamp)
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWithCreationTime(expectedTimestamp)
                );
    }

    @Test
    @DisplayName("Saves the task with the hash of the content of the original image")
    void save_task_with_original_image_hash() throws Exception {
        var command = aCommand();

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWithImageHash(IMAGE_HASH)
                );
    }

    @Test
    @DisplayName("Saves the accessible URL for the rescaled image in the task and returns it")
    void save_accessible_url_and_return_it() throws Exception {
        var command = aCommand();
        var expectedImageUrl = "testingImageUrl";

        var result = useCaseWith((filename, imageData) -> expectedImageUrl)
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertThat(result.get())
                .isEqualTo(expectedImageUrl);
        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWithImageUrl(expectedImageUrl)
                );
    }

    private RescaleImageCommand aCommand() throws IOException {
        return aCommandWith(400, 300);
    }

    private RescaleImageCommand aCommandWith(int desiredWidth, int desiredHeight) throws IOException {
        return new RescaleImageCommand(TASK_ID, anImage(), desiredWidth, desiredHeight);
    }

    private static byte[] anImage() throws IOException {
        return createImageWithSize(800, 600);
    }

    private RescaleImage useCase() {
        return new RescaleImageUseCase(repository, newsNowsClock, imageStorage);
    }

    private RescaleImage useCaseWith(NewsNowClock newsNowClock) {
        return new RescaleImageUseCase(repository, newsNowClock, imageStorage);
    }

    private RescaleImage useCaseWith(ImageStorage imageStorage) {
        return new RescaleImageUseCase(repository, newsNowsClock, imageStorage);
    }
}
