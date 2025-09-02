package com.newsnow.platform.imagerescale.adapters.driven;

import com.newsnow.platform.imagerescale.core.CannotReadFromOriginalImageException;
import com.newsnow.platform.imagerescale.core.CannotRescaleImageException;
import com.newsnow.platform.imagerescale.core.CannotWriteRescaledImageException;
import com.newsnow.platform.imagerescale.core.domain.ImageResolution;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.WIDTH;
import static helpers.ImageContentMother.aRescaledImage;
import static helpers.ImageContentMother.aTruncatedImage;
import static helpers.ImageContentMother.anImage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

final class ThumbnailatorImageRescaleServiceShould {

    private ThumbnailatorImageRescaleService service;

    @BeforeEach
    void setUp() {
        service = new ThumbnailatorImageRescaleService();
    }

    @Test
    @DisplayName("Rescale an image to the given dimensions")
    void rescale_image() throws IOException {
        var originalImage = anImage();
        var desiredResolution = new ImageResolution(WIDTH, HEIGHT);

        var rescaledImage = service.rescale(originalImage, desiredResolution);

        assertThat(rescaledImage)
                .isEqualTo(aRescaledImage());
    }

    @Test
    @DisplayName("Fail to rescale an image because it cannot read the original image")
    void cannot_read_original_image() throws IOException {
        var truncatedImage = aTruncatedImage();
        var desiredResolution = new ImageResolution(WIDTH, HEIGHT);

        assertThatException()
                .isThrownBy(() -> service.rescale(truncatedImage, desiredResolution))
                .isInstanceOf(CannotReadFromOriginalImageException.class);
    }

    @Test
    @DisplayName("Fail to rescale image")
    void cannot_rescale_image() throws IOException {
        var originalImage = anImage();
        var desiredResolution = new ImageResolution(WIDTH, HEIGHT);

        try (var mockedThumbnails = partiallyMocked(Thumbnails.class)) {
            mockedThumbnails.when(() -> Thumbnails.of(any(BufferedImage.class)))
                    .thenThrow(new RuntimeException("Cannot write image"));

            assertThatException()
                    .isThrownBy(() -> service.rescale(originalImage, desiredResolution))
                    .isInstanceOf(CannotRescaleImageException.class);
        }
    }

    @Test
    @DisplayName("Fail to write rescaled image")
    void cannot_write_rescaled_image() throws IOException {
        var originalImage = anImage();
        var desiredResolution = new ImageResolution(WIDTH, HEIGHT);

        try (var mockedImageIO = partiallyMocked(ImageIO.class)) {
            mockedImageIO.when(() -> ImageIO.write(any(BufferedImage.class), eq("png"), any(ByteArrayOutputStream.class)))
                    .thenThrow(new IOException("Cannot write image"));

            assertThatException()
                    .isThrownBy(() -> service.rescale(originalImage, desiredResolution))
                    .isInstanceOf(CannotWriteRescaledImageException.class);
        }
    }

    private static <T> MockedStatic<T> partiallyMocked(Class<T> clazz) {
        return Mockito.mockStatic(clazz, CALLS_REAL_METHODS);
    }
}
