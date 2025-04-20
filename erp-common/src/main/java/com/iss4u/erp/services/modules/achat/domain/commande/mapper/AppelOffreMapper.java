package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.appeloffre.request.AppelOffreRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.appeloffre.response.AppelOffreResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.AppelOffre;

@Mapper(componentModel = "spring")
public interface AppelOffreMapper {
    AppelOffreResponse toResponse(AppelOffre entity);

    AppelOffre toEntity(AppelOffreRequest dto);

    void updateFromDto(AppelOffreRequest dto, @MappingTarget AppelOffre entity);
}