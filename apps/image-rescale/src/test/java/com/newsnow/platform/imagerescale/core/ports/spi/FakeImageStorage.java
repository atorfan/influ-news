package com.newsnow.platform.imagerescale.core.ports.spi;

import java.util.Arrays;

public final class FakeImageStorage implements ImageStorage {

    private final String expectedImageUrl;
    private String filename;
    private byte[] storedImageData;

    public FakeImageStorage(String expectedImageUrl) {
        this.expectedImageUrl = expectedImageUrl;
    }

    @Override
    public String store(String filename, byte[] imageData) {
        this.filename = filename;
        this.storedImageData = imageData;
        return expectedImageUrl;
    }

    public boolean hasBeenCalledWith(String filename, byte[] imageData) {
        return Arrays.equals(this.storedImageData, imageData) && this.filename.equals(filename);
    }

    public boolean neverCalled() {
        return filename == null && storedImageData == null;
    }
}
