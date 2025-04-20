package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.taxesfrais.request.TaxesFraisRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.taxesfrais.response.TaxesFraisResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.TaxesFrais;

@Mapper(componentModel = "spring")
public interface TaxesFraisMapper {
    TaxesFraisResponse toResponse(TaxesFrais entity);

    TaxesFrais toEntity(TaxesFraisRequest dto);

    void updateFromDto(TaxesFraisRequest dto, @MappingTarget TaxesFrais entity);
}