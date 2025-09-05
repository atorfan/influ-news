package com.newsnow.platform.imagerescale.adapters.driver.restapi;

import java.time.LocalDateTime;
import java.util.UUID;

record RescaleImageTaskRestApiDto(
        UUID id,
        LocalDateTime createdAt,
        String originalImageHash,
        int width,
        int height,
        String imageUrl
)
        implements RestApiResponse {
}
