package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.api.FindRescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.api.UseCaseResult;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;

import java.util.Optional;
import java.util.UUID;

@UseCase
public final class FindRescaleImageTaskUseCase implements FindRescaleImageTask {

    private final RescaleImageTaskRepository repository;

    public FindRescaleImageTaskUseCase(RescaleImageTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public UseCaseResult<RescaleImageTask> apply(UUID taskId) {
        return Optional.ofNullable(repository.findBy(taskId))
                .map(UseCaseResult::success)
                .orElseGet(() -> UseCaseResult.failure("Cannot find any rescale image task with this id"));
    }
}
