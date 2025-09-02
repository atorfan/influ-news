package com.newsnow.platform.imagerescale.core;

import java.io.Serial;

public final class CannotWriteRescaledImageException extends DomainException {

    @Serial
    private static final long serialVersionUID = 9176753137188918668L;

    public CannotWriteRescaledImageException(Exception cause) {
        super("Cannot write rescaled image", cause);
    }
}
