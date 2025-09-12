package com.iss4u.erp.services.modules.achat.domain.fournisseur.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.request.FournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.response.FournisseurResponse;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {
    FournisseurResponse toResponse(Fournisseur entity);

    Fournisseur toEntity(FournisseurRequest dto);

    void updateFromDto(FournisseurRequest dto, @MappingTarget Fournisseur entity);
}