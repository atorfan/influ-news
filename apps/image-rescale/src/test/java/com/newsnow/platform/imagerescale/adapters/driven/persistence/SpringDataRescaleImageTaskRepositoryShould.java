package com.newsnow.platform.imagerescale.adapters.driven.persistence;

import com.newsnow.platform.imagerescale.infrastructure.configuration.DatabaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.WIDTH;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TEST_IMAGE_PATH;
import static helpers.ImageContentMother.IMAGE_HASH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseIntegrationTest
class SpringDataRescaleImageTaskRepositoryShould {

    @Autowired
    private JpaRescaleImageTaskRepository rescaleImageTaskRepository;

    @Test
    void testSaveAndRetrieve() {
        var taskId = UUID.randomUUID();
        var expectedTimestamp = LocalDateTime.now();
        var entityToSave = new RescaleImageTaskJpaEntity(
                taskId,
                expectedTimestamp,
                IMAGE_HASH,
                WIDTH,
                HEIGHT,
                TEST_IMAGE_PATH
        );
        rescaleImageTaskRepository.save(entityToSave);

        var entitySaved = rescaleImageTaskRepository.findById(taskId);

        assertTrue(entitySaved.isPresent());
        assertThat(entitySaved.get())
                .isEqualTo(entityToSave);
    }
}
