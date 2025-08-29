package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;

@UseCase
final class RescaleImageUseCase implements RescaleImage {

    @Override
    public String apply(RescaleImageCommand command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
