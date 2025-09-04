package com.newsnow.platform.imagerescale.adapters.driver;

import com.newsnow.platform.imagerescale.infrastructure.configuration.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.WIDTH;
import static com.newsnow.platform.imagerescale.core.domain.RescaleImageTaskMother.TEST_IMAGE_PATH;
import static helpers.ImageContentMother.anImage;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RescaleImageTaskPostController.class)
@Import(TestConfiguration.class)
final class RescaleImageTaskPostControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void resize_image_and_retrieve_accessible_url() throws Exception {
        var taskId = UUID.randomUUID();
        var image = anImage();

        requestRescaleFor(taskId, image, WIDTH, HEIGHT)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessibleImageUrl").value(TEST_IMAGE_PATH));
    }

    private ResultActions requestRescaleFor(
            UUID taskId,
            byte[] image,
            int width,
            int height
    ) throws Exception {

        var imageFile = new MockMultipartFile(
                "image",
                "test.png",
                "image/png",
                image
        );
        return mockMvc.perform(multipart("/task")
                .file(imageFile)
                .param("id", taskId.toString())
                .param("width", String.valueOf(width))
                .param("height", String.valueOf(height)));
    }
}
