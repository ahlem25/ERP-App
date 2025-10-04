package com.iss4u.erp.services.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    
    @Value("${keycloak.server-url:http://localhost:8080}")
    private String serverUrl;
    
    @Value("${keycloak.realm:master}")
    private String realm;
    
    @Value("${keycloak.client-id:admin-cli}")
    private String clientId;
    
    @Value("${keycloak.username:admin}")
    private String username;
    
    @Value("${keycloak.password:admin}")
    private String password;
    
    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
