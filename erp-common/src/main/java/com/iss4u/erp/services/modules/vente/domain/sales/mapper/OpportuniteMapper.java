package com.iss4u.erp.services.modules.vente.domain.sales.mapper;

import com.iss4u.erp.services.modules.vente.domain.sales.dto.opportunite.request.OpportuniteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.opportunite.response.OpportuniteResponse;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Opportunite;

@Mapper(componentModel = "spring")
public interface OpportuniteMapper {
    OpportuniteResponse toResponse(Opportunite entity);
    Opportunite toEntity(OpportuniteRequest dto);
    void updateFromDto(OpportuniteRequest dto, @MappingTarget Opportunite entity);
}