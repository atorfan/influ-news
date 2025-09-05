package com.newsnow.platform.imagerescale.adapters.driver.restapi;

import com.newsnow.platform.imagerescale.core.ports.api.FindRescaleImageTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/task/{taskId}")
final class RescaleImageTaskGetController {

    private static final Logger LOGGER = Logger.getLogger(RescaleImageTaskGetController.class.getName());

    private final FindRescaleImageTask findRescaleImageTask;
    private final RescaleImageTaskRestApiMapper mapper;

    RescaleImageTaskGetController(FindRescaleImageTask findRescaleImageTask, RescaleImageTaskRestApiMapper mapper) {
        this.findRescaleImageTask = findRescaleImageTask;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<RestApiResponse> perform(@PathVariable("taskId") UUID taskId) {
        var findRescaleImageTaskResult = findRescaleImageTask.apply(taskId);

        if (findRescaleImageTaskResult.hasErrors()) {
            findRescaleImageTaskResult.consumeErrors((error) ->
                    LOGGER.log(Level.WARNING, "[id: {0}] Error trying to find the rescale image task: {1}", new String[] {taskId.toString(), error})
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RestApiErrorMessageResponse("Error trying to find the rescale image task, it may not have been found for taskId"));
        }

        RestApiResponse restApiResponse = mapper.toRestApiDto(findRescaleImageTaskResult.get());

        return ResponseEntity.status(HttpStatus.OK)
                .body(restApiResponse);
    }
}
