package com.newsnow.platform.imagerescale.adapters.driven;

import com.newsnow.platform.imagerescale.core.CannotReadFromOriginalImageException;
import com.newsnow.platform.imagerescale.core.CannotRescaleImageException;
import com.newsnow.platform.imagerescale.core.CannotWriteRescaledImageException;
import com.newsnow.platform.imagerescale.core.domain.ImageResolution;
import com.newsnow.platform.imagerescale.core.ports.spi.ImageRescaleService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
final class ThumbnailatorImageRescaleService implements ImageRescaleService {

    @Override
    public byte[] rescale(byte[] imageData, ImageResolution resolution) {
        var originalImage = bufferedImageFrom(imageData);
        var rescaledImage = rescaleImage(originalImage, resolution);
        return toByteArray(rescaledImage);
    }

    private static BufferedImage rescaleImage(BufferedImage originalImage, ImageResolution resolution) {
        try {
            return Thumbnails.of(originalImage)
                    .size(resolution.width(), resolution.height())
                    .asBufferedImage();
        } catch (Exception e) {
            throw new CannotRescaleImageException(e);
        }
    }

    private static BufferedImage bufferedImageFrom(byte[] imageData) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            throw new CannotReadFromOriginalImageException(e);
        }
    }

    private static byte[] toByteArray(BufferedImage bufferedImage) {
        try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new CannotWriteRescaledImageException(e);
        }
    }
}
