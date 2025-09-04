package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.api.FindRescaleImageTask;

import java.util.UUID;

@UseCase
public final class FindRescaleImageTaskUseCase implements FindRescaleImageTask {

    @Override
    public RescaleImageTask apply(UUID taskId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
