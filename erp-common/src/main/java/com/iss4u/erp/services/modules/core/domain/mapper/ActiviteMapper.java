package com.iss4u.erp.services.modules.core.domain.mapper;

import com.iss4u.erp.services.modules.core.domain.dto.activite.request.ActiviteRequest;
import com.iss4u.erp.services.modules.core.domain.dto.activite.response.ActiviteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.models.Activite;

@Mapper(componentModel = "spring")
public interface ActiviteMapper {
    ActiviteResponse toResponse(Activite entity);

    Activite toEntity(ActiviteRequest dto);

    void updateFromDto(ActiviteRequest dto, @MappingTarget Activite entity);
}