package com.newsnow.platform.imagerescale.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.WIDTH;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.anImageResolutionWith;

public final class RescaleImageTaskMother {

    public static final UUID TASK_ID = UUID.fromString("0198f767-3114-772d-a661-5df4fb1afcc3");
    public static final String IMAGE_HASH = "40c74f4d51d67ff197321853f929e4a8";
    public static final LocalDateTime TASK_CREATED_AT = LocalDateTime.parse("2025-04-03T22:15:00");

    private RescaleImageTaskMother() {
    }

    public static RescaleImageTask aRescaleImageTaskWith(
            UUID id,
            LocalDateTime createdAt,
            String originalImageHash,
            int width,
            int height
    ) {
        return new RescaleImageTask(
                id,
                createdAt,
                new ImageHash(originalImageHash),
                anImageResolutionWith(width, height)
        );
    }

    public static RescaleImageTask aRescaleImageTaskWith(int width, int height) {
        return aRescaleImageTaskWith(
                TASK_ID,
                TASK_CREATED_AT,
                IMAGE_HASH,
                width,
                height
        );
    }

    public static RescaleImageTask aRescaleImageTaskWith(LocalDateTime createdAt) {
        return aRescaleImageTaskWith(
                TASK_ID,
                createdAt,
                IMAGE_HASH,
                WIDTH,
                HEIGHT
        );
    }

    public static RescaleImageTask aRescaleImageTaskWith(String imageHash) {
        return aRescaleImageTaskWith(
                TASK_ID,
                TASK_CREATED_AT,
                imageHash,
                WIDTH,
                HEIGHT
        );
    }
}
