package com.iss4u.erp.services.modules.vente.domain.payment.mapper;

import com.iss4u.erp.services.modules.vente.domain.payment.dto.paiement.request.PaiementRequest;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.paiement.response.PaiementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.payment.models.Paiement;

@Mapper(componentModel = "spring")
public interface PaiementMapper {
    PaiementResponse toResponse(Paiement entity);
    Paiement toEntity(PaiementRequest dto);
    void updateFromDto(PaiementRequest dto, @MappingTarget Paiement entity);
}