package com.newsnow.platform.imagerescale.core.domain;

public final class ImageResolutionMother {

    public static final int ORIGINAL_WIDTH = 800;
    public static final int ORIGINAL_HEIGHT = 600;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private ImageResolutionMother() {
    }

    public static ImageResolution anImageResolutionWith(int width, int height) {
        return new ImageResolution(width, height);
    }
}
