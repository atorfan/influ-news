package com.newsnow.platform.imagerescale.core;

import java.io.Serial;

public final class CannotReadFromOriginalImageException extends DomainException {

    @Serial
    private static final long serialVersionUID = -1341314910258247852L;

    public CannotReadFromOriginalImageException(Exception cause) {
        super("Cannot read from original image", cause);
    }
}
