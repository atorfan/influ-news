package com.newsnow.platform.imagerescale.core.domain;

import helpers.ImageContentMother;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.anImageResolutionWith;

public final class RescaleImageTaskMother {

    public static final UUID TASK_ID = UUID.randomUUID();
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final String ORIGINAL_IMAGE_HASH = ImageContentMother.IMAGE_HASH;
    public static final int WIDTH = ImageResolutionMother.WIDTH;
    public static final int HEIGHT = ImageResolutionMother.HEIGHT;
    public static final String IMAGE_URL = "/images/test.png";

    private RescaleImageTaskMother() {
    }

    public static RescaleImageTask aRescaleImageTask() {
        return new RescaleImageTask(
                TASK_ID,
                CREATED_AT,
                new ImageHash(ORIGINAL_IMAGE_HASH),
                anImageResolutionWith(WIDTH, HEIGHT),
                IMAGE_URL
        );
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
