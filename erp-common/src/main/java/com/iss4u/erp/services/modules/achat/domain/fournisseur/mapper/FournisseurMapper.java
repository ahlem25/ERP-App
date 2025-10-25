package com.iss4u.erp.services.modules.achat.domain.fournisseur.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.request.FournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.response.FournisseurResponse;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {
    
    @Mapping(target = "groupeDeFournisseursId", source = "groupeDeFournisseurs.id")
    @Mapping(target = "groupeDeFournisseursNom", source = "groupeDeFournisseurs.nomDuGroupe")
    @Mapping(target = "listeDePrixId", source = "listeDePrix.id")
    @Mapping(target = "listeDePrixNom", source = "listeDePrix.nomListeDePrix")
    FournisseurResponse toResponse(Fournisseur entity);

    @Mapping(target = "groupeDeFournisseurs", ignore = true)
    @Mapping(target = "listeDePrix", ignore = true)
    @Mapping(target = "facturesAchat", ignore = true)
    Fournisseur toEntity(FournisseurRequest dto);

    @Mapping(target = "groupeDeFournisseurs", ignore = true)
    @Mapping(target = "listeDePrix", ignore = true)
    @Mapping(target = "facturesAchat", ignore = true)
    void updateFromDto(FournisseurRequest dto, @MappingTarget Fournisseur entity);
}