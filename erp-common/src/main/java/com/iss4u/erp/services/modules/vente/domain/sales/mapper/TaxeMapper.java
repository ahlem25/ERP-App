package com.iss4u.erp.services.modules.vente.domain.sales.mapper;

import com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.request.TaxeRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.response.TaxeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;

@Mapper(componentModel = "spring")
public interface TaxeMapper {
    TaxeResponse toResponse(Taxe entity);
    Taxe toEntity(TaxeRequest dto);
    void updateFromDto(TaxeRequest dto, @MappingTarget Taxe entity);
}