package com.newsnow.platform.imagerescale.adapters.driven.persistence;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@Fallback
public class InMemoryRescaleImageTaskRepository implements RescaleImageTaskRepository {

    private final Map<String, RescaleImageTask> savedTasks = new HashMap<>();

    @Override
    public RescaleImageTask findBy(final UUID taskId) {
        return savedTasks.get(taskId.toString());
    }

    @Override
    public void save(RescaleImageTask task) {
        this.savedTasks.put(task.id().toString(), task);
    }
}
