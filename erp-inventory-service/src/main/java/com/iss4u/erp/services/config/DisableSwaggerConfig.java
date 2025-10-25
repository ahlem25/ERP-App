package com.iss4u.erp.services.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "false", matchIfMissing = true)
public class DisableSwaggerConfig {
    // Configuration pour désactiver Swagger
}
