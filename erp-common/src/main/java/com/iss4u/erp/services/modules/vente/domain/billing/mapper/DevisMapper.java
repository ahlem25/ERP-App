package com.iss4u.erp.services.modules.vente.domain.billing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.devis.request.DevisRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.devis.response.DevisResponse;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;

@Mapper(componentModel = "spring")
public interface DevisMapper {
    DevisResponse toResponse(Devis entity);
    Devis toEntity(DevisRequest dto);
    void updateFromDto(DevisRequest dto, @MappingTarget Devis entity);
}