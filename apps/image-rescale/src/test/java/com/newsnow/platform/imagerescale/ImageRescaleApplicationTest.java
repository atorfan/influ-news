package com.newsnow.platform.imagerescale;

import com.newsnow.platform.imagerescale.infrastructure.persistence.DatabaseTestInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = ImageRescaleApplication.class)
@ContextConfiguration(initializers = DatabaseTestInitializer.class)
class ImageRescaleApplicationTest {

    @Test
    void contextLoads() {
    }
}
