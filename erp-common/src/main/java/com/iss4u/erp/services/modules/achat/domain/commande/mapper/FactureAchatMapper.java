package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.FactureAchatRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.response.FactureAchatResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;

@Mapper(componentModel = "spring")
public interface FactureAchatMapper {
    
    @Mapping(target = "articlesFactures", ignore = true)
    FactureAchatResponse toResponse(FactureAchat entity);

    @Mapping(target = "articlesFactures", ignore = true)
    FactureAchat toEntity(FactureAchatRequest dto);

    @Mapping(target = "articlesFactures", ignore = true)
    void updateFromDto(FactureAchatRequest dto, @MappingTarget FactureAchat entity);
}