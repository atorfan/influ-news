package com.newsnow.platform.imagerescale.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record RescaleImageTask(
        UUID id,
        LocalDateTime createdAt,
        ImageHash originalImageHash,
        ImageResolution appliedResolution
) {
}
