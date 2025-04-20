package com.iss4u.erp.services.modules.core.domain.mapper;

import com.iss4u.erp.services.modules.core.domain.dto.profilrole.request.ProfilRoleRequest;
import com.iss4u.erp.services.modules.core.domain.dto.profilrole.response.ProfilRoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.models.ProfilRole;

@Mapper(componentModel = "spring")
public interface ProfilRoleMapper {
    ProfilRoleResponse toResponse(ProfilRole entity);

    ProfilRole toEntity(ProfilRoleRequest dto);

    void updateFromDto(ProfilRoleRequest dto, @MappingTarget ProfilRole entity);
}