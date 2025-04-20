package com.iss4u.erp.services.modules.vente.domain.sales.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.request.SocieteRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.response.SocieteResponse;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

@Mapper(componentModel = "spring")
public interface SocieteMapper {
    SocieteResponse toResponse(Societe entity);
    Societe toEntity(SocieteRequest dto);
    void updateFromDto(SocieteRequest dto, @MappingTarget Societe entity);
}