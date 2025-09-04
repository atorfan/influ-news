package com.newsnow.platform.imagerescale.core.ports.api;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;

import java.util.UUID;

public interface FindRescaleImageTask {

    UseCaseResult<RescaleImageTask> apply(UUID taskId);
}
