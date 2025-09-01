package com.newsnow.platform.imagerescale.adapters.driven;

import com.newsnow.platform.imagerescale.core.domain.ImageResolution;
import com.newsnow.platform.imagerescale.core.ports.spi.ImageRescaleService;
import com.newsnow.platform.imagerescale.core.ports.spi.ImageStorage;
import org.springframework.stereotype.Component;

@Component
final class ThumbnailatorImageRescaleService implements ImageRescaleService {

    @Override
    public byte[] rescale(byte[] imageData, ImageResolution resolution) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
