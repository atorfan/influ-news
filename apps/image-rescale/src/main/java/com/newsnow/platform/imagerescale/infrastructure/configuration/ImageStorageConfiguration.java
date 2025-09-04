package com.newsnow.platform.imagerescale.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.newsnow.platform.imagerescale.adapters.driven.imagestorage")
public class ImageStorageConfiguration {
}
