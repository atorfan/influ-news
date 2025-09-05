package com.newsnow.platform.imagerescale.adapters.driver.restapi;

import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/task")
final class RescaleImageTaskPostController {

    private static final Logger LOGGER = Logger.getLogger(RescaleImageTaskPostController.class.getName());

    private final RescaleImage rescaleImage;

    RescaleImageTaskPostController(RescaleImage rescaleImage) {
        this.rescaleImage = rescaleImage;
    }

    @PostMapping
    public ResponseEntity<RestApiResponse> perform(
            @RequestParam("id") UUID id,
            @RequestParam("image") MultipartFile image,
            @RequestParam("width") int width,
            @RequestParam("height") int height
    ) throws IOException {

        var command = new RescaleImageCommand(id, image.getBytes(), width, height);
        var rescaleImageTaskResult = rescaleImage.apply(command);

        if (rescaleImageTaskResult.hasErrors()) {
            rescaleImageTaskResult.consumeErrors((error) ->
                    LOGGER.log(Level.WARNING, "[id: {0}] Error trying to rescale the image: {1}", new String[] {id.toString(), error})
            );
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new RestApiErrorMessageResponse("Error trying to rescale the image, contact with the maintainer of the application"));
        }

        RestApiResponse restApiResponse = new RescaleImageTaskUrlResponse(rescaleImageTaskResult.get());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restApiResponse);
    }
}
