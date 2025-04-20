package com.iss4u.erp.services.modules.vente.domain.sales.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.profilpdv.request.ProfilPDVRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.profilpdv.response.ProfilPDVResponse;
import com.iss4u.erp.services.modules.vente.domain.sales.models.ProfilPDV;

@Mapper(componentModel = "spring")
public interface ProfilPDVMapper {
    ProfilPDVResponse toResponse(ProfilPDV entity);
    ProfilPDV toEntity(ProfilPDVRequest dto);
    void updateFromDto(ProfilPDVRequest dto, @MappingTarget ProfilPDV entity);
}