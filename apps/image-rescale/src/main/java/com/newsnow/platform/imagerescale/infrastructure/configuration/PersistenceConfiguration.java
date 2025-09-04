package com.newsnow.platform.imagerescale.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.newsnow.platform.imagerescale.adapters.driven.persistence")
class PersistenceConfiguration {
}
