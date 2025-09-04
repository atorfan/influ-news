package com.newsnow.platform.imagerescale.adapters.driven.persistence;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Fallback
public class InMemoryRescaleImageTaskRepository implements RescaleImageTaskRepository {

    private final Map<String, RescaleImageTask> savedTasks;

    public InMemoryRescaleImageTaskRepository() {
        this(new RescaleImageTask[0]);
    }

    public InMemoryRescaleImageTaskRepository(RescaleImageTask... tasks) {
        this.savedTasks = Arrays.stream(tasks)
                .collect(Collectors.toMap(task -> task.id().toString(), task -> task));
    }

    @Override
    public RescaleImageTask findBy(final UUID taskId) {
        return savedTasks.get(taskId.toString());
    }

    @Override
    public void save(RescaleImageTask task) {
        this.savedTasks.put(task.id().toString(), task);
    }
}
