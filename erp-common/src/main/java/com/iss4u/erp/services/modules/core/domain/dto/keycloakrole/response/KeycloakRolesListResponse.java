package com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakRolesListResponse {
    private Boolean success;
    private String realm;
    private Integer totalRoles;
    private List<KeycloakRoleResponse> roles;
    private String message;
}
