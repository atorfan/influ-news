package com.newsnow.platform.imagerescale.core.ports.spi;

public interface ImageStorage {

    String store(String filename, byte[] imageData);
}
