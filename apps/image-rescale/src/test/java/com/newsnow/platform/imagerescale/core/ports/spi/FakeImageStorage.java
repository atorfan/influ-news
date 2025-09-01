package com.newsnow.platform.imagerescale.core.ports.spi;

public final class FakeImageStorage implements ImageStorage {

    private final String expectedImageUrl;
    private byte[] storedImageData;

    public FakeImageStorage(String expectedImageUrl) {
        this.expectedImageUrl = expectedImageUrl;
    }

    @Override
    public String store(String filename, byte[] imageData) {
        this.storedImageData = imageData;
        return expectedImageUrl;
    }

    public byte[] storedImage() {
        return storedImageData;
    }
}
