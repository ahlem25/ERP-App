package com.iss4u.erp.services.config;

import com.iss4u.erp.services.properties.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.Components;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

/** Please visit this link for API documentation:
 * http://SERVICE-URL/swagger-ui.html
 * or
 * http://SERVICE-URL/swagger-ui/index.html
 *
 * This Docs is availible only for dev & test profile (not for pprd & prd)
 */
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {
    private final SwaggerProperties swaggerProperties;
    private final Environment environment;
    @Bean
    public OpenAPI customOpenAPI() {
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (activeProfiles.contains("prd") || activeProfiles.contains("pprd")) {
            return null; // Swagger is Disabled
        }
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title(swaggerProperties.getTitle())
                        .description(swaggerProperties.getDescription())
                        .version(swaggerProperties.getVersion())
                        .termsOfService(swaggerProperties.getTermsOfServiceUrl())
                        .license(apiLicense())
                        .contact(apiContact())
                ).components(apiComponents());
               // .addSecurityItem(oAuth2SecurityRequirement());
    }

    private License apiLicense() {
        return new License()
                .name(swaggerProperties.getLicense())
                .url(swaggerProperties.getLicenseUrl());
    }

    private Contact apiContact() {
        return new Contact()
                .name(swaggerProperties.getContact().getName())
                .url(swaggerProperties.getContact().getUrl())
                .email(swaggerProperties.getContact().getEmail());
    }

    private Components apiComponents(){
        return new Components();
//                .addSecuritySchemes("keycloak", new SecurityScheme()
//                        .type(SecurityScheme.Type.OAUTH2)
//                        .flows(new OAuthFlows()
//                                .authorizationCode(new OAuthFlowAuthorizationCode()
//                                        .authorizationUrl("http://localhost:8080/realms/myrealm/protocol/openid-connect/auth")
//                                        .tokenUrl("http://localhost:8080/realms/myrealm/protocol/openid-connect/token")
//                                )
//                        )
//                );
    }

    private SecurityRequirement oAuth2SecurityRequirement() {
        return new SecurityRequirement().addList("keycloak");
    }
}