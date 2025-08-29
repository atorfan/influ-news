package com.newsnow.platform.imagerescale.core.ports.api;

import java.util.UUID;

public record RescaleImageCommand(
        UUID id,
        byte[] imageData,
        int targetImageWidth,
        int targetImageHeight
) {
}
