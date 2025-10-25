package com.iss4u.erp.services.modules.vente.domain.payment.mapper;

import com.iss4u.erp.services.modules.vente.domain.payment.dto.methodepaiement.request.MethodePaiementRequest;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.methodepaiement.response.MethodePaiementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.payment.models.MethodePaiement;

@Mapper(componentModel = "spring")
public interface MethodePaiementMapper {
    MethodePaiementResponse toResponse(MethodePaiement entity);
    MethodePaiement toEntity(MethodePaiementRequest dto);
    void updateFromDto(MethodePaiementRequest dto, @MappingTarget MethodePaiement entity);
}