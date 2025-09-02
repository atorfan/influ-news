package com.newsnow.platform.imagerescale.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.anImageResolutionWith;

public final class RescaleImageTaskMother {

    private RescaleImageTaskMother() {
    }

    public static RescaleImageTask aRescaleImageTaskWith(
            UUID id,
            LocalDateTime createdAt,
            String originalImageHash,
            int width,
            int height,
            String imageUrl
    ) {
        return new RescaleImageTask(
                id,
                createdAt,
                new ImageHash(originalImageHash),
                anImageResolutionWith(width, height),
                imageUrl
        );
    }
}
