package com.newsnow.platform.imagerescale.core.ports.spi;

import com.newsnow.platform.imagerescale.core.domain.ImageResolution;

public interface ImageRescaleService {

    byte[] rescale(byte[] imageData, ImageResolution resolution);
}
