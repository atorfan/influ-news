package com.newsnow.platform.imagerescale.adapters.driver.restapi;

import com.newsnow.platform.imagerescale.core.ports.api.FindRescaleImageTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/task/{taskId}")
final class RescaleImageTaskGetController {

    private final FindRescaleImageTask findRescaleImageTask;
    private final RescaleImageTaskRestApiMapper mapper;

    RescaleImageTaskGetController(FindRescaleImageTask findRescaleImageTask, RescaleImageTaskRestApiMapper mapper) {
        this.findRescaleImageTask = findRescaleImageTask;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<RescaleImageTaskRestApiDto> perform(@PathVariable("taskId") UUID taskId) {
        var rescaleImageTask = findRescaleImageTask.apply(taskId);
        return ResponseEntity.ok(
                mapper.toRestApiDto(rescaleImageTask)
        );
    }
}
