package com.newsnow.platform.imagerescale.adapters.driven;

import com.newsnow.platform.imagerescale.core.ports.spi.ImageStorage;
import org.springframework.stereotype.Component;

@Component
final class AwsImageStorage implements ImageStorage {

    @Override
    public String store(String filename, byte[] imageData) {
        throw new UnsupportedOperationException();
    }
}
