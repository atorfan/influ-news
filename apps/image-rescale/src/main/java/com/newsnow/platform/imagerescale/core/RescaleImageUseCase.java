package com.newsnow.platform.imagerescale.core;

import com.newsnow.platform.imagerescale.core.domain.ImageHash;
import com.newsnow.platform.imagerescale.core.domain.ImageResolution;
import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import com.newsnow.platform.imagerescale.core.ports.api.UseCaseResult;
import com.newsnow.platform.imagerescale.core.ports.spi.NewsNowClock;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import com.newsnow.platform.imagerescale.core.ports.spi.ImageStorage;

@UseCase
public final class RescaleImageUseCase implements RescaleImage {

    private final RescaleImageTaskRepository repository;
    private final NewsNowClock clock;
    private final ImageStorage imageStorage;

    public RescaleImageUseCase(RescaleImageTaskRepository repository, NewsNowClock clock, ImageStorage imageStorage) {
        this.repository = repository;
        this.clock = clock;
        this.imageStorage = imageStorage;
    }

    @Override
    public UseCaseResult<String> apply(RescaleImageCommand command) {
        try {
            var imageFilename = command.id().toString();
            var accessibleImageUrl = imageStorage.store(imageFilename, command.imageData());

            var desiredResolution = new ImageResolution(command.targetImageWidth(), command.targetImageHeight());
            var originalImageHash = ImageHash.from(command.imageData());
            var task = new RescaleImageTask(
                    command.id(),
                    clock.currentTimestamp(),
                    originalImageHash,
                    desiredResolution,
                    accessibleImageUrl
            );
            repository.save(task);

            return UseCaseResult.success(accessibleImageUrl);

        } catch (InvalidImageResolutionException e) {
            return UseCaseResult.failure("Cannot rescale image: invalid resolution specified");
        } catch (ImageHashingException e) {
            return UseCaseResult.failure("Cannot obtain the hash from the original image: " + e.getMessage());
        }
    }
}
