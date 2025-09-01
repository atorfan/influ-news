package com.newsnow.platform.imagerescale.core.ports.spi;

import com.newsnow.platform.imagerescale.core.domain.ImageResolution;

public final class FakeImageRescaleService implements ImageRescaleService {

    private final byte[] expectedImageData;
    private byte[] originalImageData;
    private ImageResolution resolution;

    public FakeImageRescaleService(byte[] expectedImageData) {
        this.expectedImageData = expectedImageData;
    }

    @Override
    public byte[] rescale(byte[] imageData, ImageResolution resolution) {
        this.originalImageData = imageData;
        this.resolution = resolution;
        return expectedImageData;
    }

    public ImageResolution appliedRescaleResolution() {
        return resolution;
    }
}
