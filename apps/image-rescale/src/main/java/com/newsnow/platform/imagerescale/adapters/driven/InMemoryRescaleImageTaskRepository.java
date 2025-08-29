package com.newsnow.platform.imagerescale.adapters.driven;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public final class InMemoryRescaleImageTaskRepository implements RescaleImageTaskRepository {

    private RescaleImageTask savedTask;

    @Override
    public RescaleImageTask findBy(final UUID taskId) {
        return savedTask;
    }

    @Override
    public void save(RescaleImageTask task) {
        this.savedTask = task;
    }
}
