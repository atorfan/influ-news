package com.newsnow.platform.imagerescale;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Rescale images service API", version = "v1", description = "API endpoints for rescale images and obtain its information"))
@SpringBootApplication
public class ImageRescaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageRescaleApplication.class, args);
    }
}
