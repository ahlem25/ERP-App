package com.iss4u.erp.services.modules.core.domain.mapper;

import com.iss4u.erp.services.modules.core.domain.dto.role.request.RoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.dto.role.response.RoleResponse;
import com.iss4u.erp.services.modules.core.domain.models.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toResponse(Role entity);

    Role toEntity(RoleRequest dto);

    void updateFromDto(RoleRequest dto, @MappingTarget Role entity);
}