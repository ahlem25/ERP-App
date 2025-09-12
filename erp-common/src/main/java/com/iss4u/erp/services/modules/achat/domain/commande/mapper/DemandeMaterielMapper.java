package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.request.DemandeMaterielRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.response.DemandeMaterielResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;

@Mapper(componentModel = "spring")
public interface DemandeMaterielMapper {
    DemandeMaterielResponse toResponse(DemandeMateriel entity);

    DemandeMateriel toEntity(DemandeMaterielRequest dto);

    void updateFromDto(DemandeMaterielRequest dto, @MappingTarget DemandeMateriel entity);
}