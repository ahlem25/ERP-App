package com.iss4u.erp.services.modules.vente.domain.billing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.request.FactureVenteRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.response.FactureVenteResponse;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;

@Mapper(componentModel = "spring")
public interface FactureVenteMapper {
    FactureVenteResponse toResponse(FactureVente entity);
    FactureVente toEntity(FactureVenteRequest dto);
    void updateFromDto(FactureVenteRequest dto, @MappingTarget FactureVente entity);
}