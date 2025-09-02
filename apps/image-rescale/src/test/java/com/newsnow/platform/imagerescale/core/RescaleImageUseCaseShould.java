package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.adapters.driven.InMemoryRescaleImageTaskRepository;
import com.newsnow.platform.imagerescale.core.domain.ImageHash;
import com.newsnow.platform.imagerescale.core.domain.ImageResolution;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import com.newsnow.platform.imagerescale.core.ports.spi.FakeImageRescaleService;
import com.newsnow.platform.imagerescale.core.ports.spi.FakeImageStorage;
import com.newsnow.platform.imagerescale.core.ports.spi.NewsNowClock;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import helpers.ImageContentMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.WIDTH;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.aRescaleImageTaskWith;
import static helpers.ImageContentMother.RESCALED_IMAGE_HASH;
import static helpers.ImageContentMother.aRescaledImage;
import static helpers.ImageContentMother.anImage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RescaleImageUseCaseShould {

    private UUID taskId;
    private static final int desiredWidth = WIDTH;
    private static final int desiredHeight = HEIGHT;
    private static final String originalImageHash = ImageContentMother.IMAGE_HASH;
    private byte[] expectedRescaledImageData;
    private String expectedStoredImageUrl;
    private LocalDateTime expectedTimestamp;

    private FakeImageRescaleService imageRescaleService;
    private FakeImageStorage imageStorage;
    private NewsNowClock newsNowClock;
    private RescaleImageTaskRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        taskId = UUID.randomUUID();
        expectedRescaledImageData = aRescaledImage();
        expectedStoredImageUrl = taskId.toString().concat(".png");
        expectedTimestamp = LocalDateTime.now();

        imageRescaleService = new FakeImageRescaleService(expectedRescaledImageData);
        imageStorage = new FakeImageStorage(expectedStoredImageUrl);
        newsNowClock = () -> expectedTimestamp;
        repository = new InMemoryRescaleImageTaskRepository();
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
        assertTrue(imageRescaleService.neverCalled());
        assertTrue(imageStorage.neverCalled());
        assertThat(repository.findBy(command.id()))
                .isNull();
    }

    @Test
    @DisplayName("Rescale the image with the desired resolution")
    void rescale_image_with_desired_resolution() throws Exception {
        var command = aCommand();
        var expectedAppliedResolution = new ImageResolution(command.targetImageWidth(), command.targetImageHeight());

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertTrue(imageRescaleService.hasBeenCalledWith(command.imageData(), expectedAppliedResolution));
        assertThat(ImageHash.from(imageRescaleService.rescaledImageData()))
                .isEqualTo(new ImageHash(RESCALED_IMAGE_HASH));
    }

    @Test
    @DisplayName("Store the image and return his accessible URL")
    void store_image_and_return_his_accessible_url() throws Exception {
        var command = aCommand();
        var expectedFilename = aCommand().id().toString();

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertTrue(imageStorage.hasBeenCalledWith(expectedFilename, expectedRescaledImageData));
        assertThat(result.get())
                .isEqualTo(expectedStoredImageUrl);
    }

    @Test
    @DisplayName("Saves image rescale task")
    void save_image_rescale_task() throws Exception {
        var command = aCommand();

        var result = useCase()
                .apply(command);

        assertThat(result.hasErrors())
                .isFalse();
        assertThat(repository.findBy(command.id()))
                .isNotNull()
                .isEqualTo(
                        aRescaleImageTaskWith(
                                taskId,
                                expectedTimestamp,
                                originalImageHash,
                                desiredWidth,
                                desiredHeight,
                                expectedStoredImageUrl
                        )
                );
    }

    private RescaleImageCommand aCommand() throws IOException {
        return aCommandWith(desiredWidth, desiredHeight);
    }

    private RescaleImageCommand aCommandWith(int desiredWidth, int desiredHeight) throws IOException {
        return new RescaleImageCommand(taskId, anImage(), desiredWidth, desiredHeight);
    }

    private RescaleImage useCase() {
        return new RescaleImageUseCase(imageRescaleService, imageStorage, newsNowClock, repository);
    }
}
