package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.commandeachat.request.CommandeAchatRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.commandeachat.response.CommandeAchatResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.CommandeAchat;

@Mapper(componentModel = "spring")
public interface CommandeAchatMapper {
    CommandeAchatResponse toResponse(CommandeAchat entity);

    CommandeAchat toEntity(CommandeAchatRequest dto);

    void updateFromDto(CommandeAchatRequest dto, @MappingTarget CommandeAchat entity);
}