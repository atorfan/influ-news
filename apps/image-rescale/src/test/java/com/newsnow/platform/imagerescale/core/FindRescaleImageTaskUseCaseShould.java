package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.adapters.driven.persistence.InMemoryRescaleImageTaskRepository;
import com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother;
import com.newsnow.platform.imagerescale.core.ports.api.FindRescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

final class FindRescaleImageTaskUseCaseShould {

    private FindRescaleImageTask findImageRescaleTask;
    private RescaleImageTaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRescaleImageTaskRepository();
        findImageRescaleTask = new FindRescaleImageTaskUseCase(repository);
    }

    @Test
    @DisplayName("Should find and return the task")
    void find_and_return_the_task() {
        var expectedTask = RescaleImageTaskMother.aRescaleImageTask();
        repository.save(expectedTask);

        var rescaleImageTask = findImageRescaleTask.apply(expectedTask.id());

        assertThat(rescaleImageTask.hasErrors())
                .isFalse();
        assertThat(rescaleImageTask.get())
                .isEqualTo(expectedTask);
    }

    @Test
    @DisplayName("Should not find any task")
    void not_find_any_task() {
        var rescaleImageTask = findImageRescaleTask.apply(UUID.randomUUID());

        assertThat(rescaleImageTask.hasErrors())
                .isTrue();
    }
}
