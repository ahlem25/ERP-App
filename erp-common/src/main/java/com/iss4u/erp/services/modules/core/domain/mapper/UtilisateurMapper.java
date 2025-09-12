package com.iss4u.erp.services.modules.core.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.dto.utilisateur.request.UtilisateurRequest;
import com.iss4u.erp.services.modules.core.domain.dto.utilisateur.response.UtilisateurResponse;
import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    UtilisateurResponse toResponse(Utilisateur entity);

    Utilisateur toEntity(UtilisateurRequest dto);

    void updateFromDto(UtilisateurRequest dto, @MappingTarget Utilisateur entity);
}