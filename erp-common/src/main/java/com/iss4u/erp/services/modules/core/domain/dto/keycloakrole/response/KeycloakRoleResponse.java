package com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakRoleResponse {
    private String id;
    private String name;
    private String description;
    private Boolean composite;
    private Boolean clientRole;
    private String containerId;
}
