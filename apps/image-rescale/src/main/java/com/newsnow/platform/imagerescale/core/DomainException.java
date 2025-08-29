package com.newsnow.platform.imagerescale.core;

abstract class DomainException extends RuntimeException {

    protected DomainException() {
    }

    protected DomainException(String message) {
        super(message);
    }

    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
