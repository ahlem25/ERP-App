package com.iss4u.erp.services.modules.achat.domain.fournisseur.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.request.GroupeFournisseursRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.response.GroupeFournisseursResponse;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.GroupeFournisseurs;

@Mapper(componentModel = "spring")
public interface GroupeFournisseursMapper {
    GroupeFournisseursResponse toResponse(GroupeFournisseurs entity);

    GroupeFournisseurs toEntity(GroupeFournisseursRequest dto);

    void updateFromDto(GroupeFournisseursRequest dto, @MappingTarget GroupeFournisseurs entity);
}