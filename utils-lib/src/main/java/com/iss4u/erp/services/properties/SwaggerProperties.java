package com.iss4u.erp.services.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private Contact contact;

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }
}