package com.newsnow.platform.imagerescale.infrastructure.configuration;

import com.newsnow.platform.imagerescale.ImageRescaleApplication;
import com.newsnow.platform.imagerescale.infrastructure.imagestorage.ImageStorageTestInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = ImageRescaleApplication.class)
@ContextConfiguration(initializers = ImageStorageTestInitializer.class)
public @interface ApplicationIntegrationTest {
}
