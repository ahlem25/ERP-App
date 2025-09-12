package com.iss4u.erp.services.modules.vente.domain.sales.mapper;

import com.iss4u.erp.services.modules.vente.domain.sales.dto.vendeur.request.VendeurRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.vendeur.response.VendeurResponse;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;

@Mapper(componentModel = "spring")
public interface VendeurMapper {
    VendeurResponse toResponse(Vendeur entity);
    Vendeur toEntity(VendeurRequest dto);
    void updateFromDto(VendeurRequest dto, @MappingTarget Vendeur entity);
}