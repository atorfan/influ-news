package com.newsnow.platform.imagerescale.core.domain;

import com.newsnow.platform.imagerescale.core.InvalidImageResolutionException;

public record ImageResolution(int width, int height) {

    public ImageResolution {
        if (width <= 0 || height <= 0) {
            throw new InvalidImageResolutionException();
        }
    }

    public static ImageResolution from(String width, String height) {
        return new ImageResolution(
                Integer.parseInt(width),
                Integer.parseInt(height)
        );
    }
}
