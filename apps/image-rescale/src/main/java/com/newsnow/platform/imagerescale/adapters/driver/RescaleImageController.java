package com.newsnow.platform.imagerescale.adapters.driver;

import com.newsnow.platform.imagerescale.core.ports.api.RescaleImage;
import com.newsnow.platform.imagerescale.core.ports.api.RescaleImageCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/task")
final class RescaleImageController {

    private final RescaleImage rescaleImage;

    RescaleImageController(RescaleImage rescaleImage) {
        this.rescaleImage = rescaleImage;
    }

    @PostMapping
    public ResponseEntity<RescaleImageResponse> perform(
            @RequestParam("id") UUID id,
            @RequestParam("image") MultipartFile image,
            @RequestParam("width") int width,
            @RequestParam("height") int height
    ) throws URISyntaxException, IOException {

        var command = new RescaleImageCommand(id, image.getBytes(), width, height);
        var result = rescaleImage.apply(command);
        var accessibleImageUrl = result.get();

        return ResponseEntity.created(new URI(""))
                .body(new RescaleImageResponse(accessibleImageUrl));
    }
}
