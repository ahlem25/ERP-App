package com.iss4u.erp.services.modules.achat.domain.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.ListePrixRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.response.ListePrixResponse;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;

@Mapper(componentModel = "spring")
public interface ListePrixMapper {
    ListePrixResponse toResponse(ListePrix entity);

    ListePrix toEntity(ListePrixRequest dto);

    void updateFromDto(ListePrixRequest dto, @MappingTarget ListePrix entity);
}