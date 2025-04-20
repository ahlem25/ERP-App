package com.iss4u.erp.services.modules.core.domain.mapper;

import com.iss4u.erp.services.modules.core.domain.dto.moduleprofile.request.ModuleProfileRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.dto.moduleprofile.response.ModuleProfileResponse;
import com.iss4u.erp.services.modules.core.domain.models.ModuleProfile;

@Mapper(componentModel = "spring")
public interface ModuleProfileMapper {
    ModuleProfileResponse toResponse(ModuleProfile entity);

    ModuleProfile toEntity(ModuleProfileRequest dto);

    void updateFromDto(ModuleProfileRequest dto, @MappingTarget ModuleProfile entity);
}