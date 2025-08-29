package com.newsnow.platform.imagerescale.core.ports.spi;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;

import java.util.UUID;

public interface RescaleImageTaskRepository {

    RescaleImageTask findBy(UUID taskId);

    void save(RescaleImageTask task);
}
