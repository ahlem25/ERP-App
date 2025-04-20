package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.request.DevisFournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.response.DevisFournisseurResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DevisFournisseur;

@Mapper(componentModel = "spring")
public interface DevisFournisseurMapper {
    DevisFournisseurResponse toResponse(DevisFournisseur entity);

    DevisFournisseur toEntity(DevisFournisseurRequest dto);

    void updateFromDto(DevisFournisseurRequest dto, @MappingTarget DevisFournisseur entity);
}