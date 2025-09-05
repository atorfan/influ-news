package com.newsnow.platform.imagerescale.adapters.driver.restapi;

import com.newsnow.platform.imagerescale.infrastructure.configuration.RestApiConfiguration;
import com.newsnow.platform.imagerescale.infrastructure.configuration.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.CREATED_AT;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.IMAGE_URL;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.ORIGINAL_IMAGE_HASH;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TASK_ID;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.WIDTH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RescaleImageTaskGetController.class)
@Import({RestApiConfiguration.class, TestConfiguration.class})
final class RescaleImageTaskGetControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieve_rescale_image_task_information() throws Exception {
        requestRescaleImageTaskFor(TASK_ID)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TASK_ID.toString()))
                .andExpect(jsonPath("$.createdAt").value(CREATED_AT.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.originalImageHash").value(ORIGINAL_IMAGE_HASH))
                .andExpect(jsonPath("$.width").value(Integer.valueOf(WIDTH)))
                .andExpect(jsonPath("$.height").value(Integer.valueOf(HEIGHT)))
                .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL))
        ;
    }

    @Test
    void not_found() throws Exception {
        var taskId = UUID.randomUUID();
        requestRescaleImageTaskFor(taskId)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
        ;
    }

    private ResultActions requestRescaleImageTaskFor(UUID taskId) throws Exception {
        return mockMvc.perform(
                get("/task/{taskId}", taskId.toString())
        );
    }
}
