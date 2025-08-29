package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.core.domain.ImageHash;
import com.newsnow.platform.imagerescale.core.domain.ImageResolution;
import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import com.newsnow.platform.imagerescale.core.ports.api.UseCaseResult;
import com.newsnow.platform.imagerescale.core.ports.spi.NewsNowClock;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;

@UseCase
public final class RescaleImageUseCase implements RescaleImage {

    private final RescaleImageTaskRepository repository;
    private final NewsNowClock clock;

    public RescaleImageUseCase(RescaleImageTaskRepository repository, NewsNowClock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public UseCaseResult<String> apply(RescaleImageCommand command) {
        try {
            var desiredResolution = new ImageResolution(command.targetImageWidth(), command.targetImageHeight());
            var task = new RescaleImageTask(
                    command.id(),
                    clock.currentTimestamp(),
                    ImageHash.from(command.imageData()),
                    desiredResolution
            );
            repository.save(task);

            return UseCaseResult.success(null);

        } catch (InvalidImageResolutionException e) {
            return UseCaseResult.failure("Cannot rescale image: invalid resolution specified");
        } catch (ImageHashingException e) {
            return UseCaseResult.failure("Cannot obtain the hash from the original image: " + e.getMessage());
        }
    }
}
