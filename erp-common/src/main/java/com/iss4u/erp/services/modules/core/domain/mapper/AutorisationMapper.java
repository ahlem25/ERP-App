package com.iss4u.erp.services.modules.core.domain.mapper;

import com.iss4u.erp.services.modules.core.domain.dto.autorisation.request.AutorisationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.dto.autorisation.response.AutorisationResponse;
import com.iss4u.erp.services.modules.core.domain.models.Autorisation;

@Mapper(componentModel = "spring")
public interface AutorisationMapper {
    AutorisationResponse toResponse(Autorisation entity);

    Autorisation toEntity(AutorisationRequest dto);

    void updateFromDto(AutorisationRequest dto, @MappingTarget Autorisation entity);
}