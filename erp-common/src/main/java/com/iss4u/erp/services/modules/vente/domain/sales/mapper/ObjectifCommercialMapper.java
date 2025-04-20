package com.iss4u.erp.services.modules.vente.domain.sales.mapper;

import com.iss4u.erp.services.modules.vente.domain.sales.dto.objectifcommercial.request.ObjectifCommercialRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.objectifcommercial.response.ObjectifCommercialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.sales.models.ObjectifCommercial;

@Mapper(componentModel = "spring")
public interface ObjectifCommercialMapper {
    ObjectifCommercialResponse toResponse(ObjectifCommercial entity);
    ObjectifCommercial toEntity(ObjectifCommercialRequest dto);
    void updateFromDto(ObjectifCommercialRequest dto, @MappingTarget ObjectifCommercial entity);
}