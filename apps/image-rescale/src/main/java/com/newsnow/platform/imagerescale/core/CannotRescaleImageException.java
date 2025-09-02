package com.newsnow.platform.imagerescale.core;

import java.io.Serial;

public final class CannotRescaleImageException extends DomainException {

    @Serial
    private static final long serialVersionUID = 9176753137188918668L;

    public CannotRescaleImageException(Throwable cause) {
        super("Cannot rescale the image" , cause);
    }
}
