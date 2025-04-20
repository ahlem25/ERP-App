package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.FactureAchatRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.response.FactureAchatResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;

@Mapper(componentModel = "spring")
public interface FactureAchatMapper {
    FactureAchatResponse toResponse(FactureAchat entity);

    FactureAchat toEntity(FactureAchatRequest dto);

    void updateFromDto(FactureAchatRequest dto, @MappingTarget FactureAchat entity);
}