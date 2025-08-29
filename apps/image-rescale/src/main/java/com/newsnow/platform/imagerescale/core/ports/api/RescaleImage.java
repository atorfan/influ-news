package com.newsnow.platform.imagerescale.core.ports.api;

public interface RescaleImage {

    UseCaseResult<String> apply(RescaleImageCommand command);
}
