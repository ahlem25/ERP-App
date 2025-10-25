package com.iss4u.erp.services.modules.vente.domain.billing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.request.CommandeClientRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.response.CommandeClientResponse;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;

@Mapper(componentModel = "spring")
public interface CommandeClientMapper {
    
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "facture", ignore = true)
    @Mapping(target = "entrepot", ignore = true)
    @Mapping(target = "taxes", ignore = true)
    @Mapping(target = "listePrix", ignore = true)
    CommandeClientResponse toResponse(CommandeClient entity);
    
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "facture", ignore = true)
    @Mapping(target = "entrepot", ignore = true)
    @Mapping(target = "taxes", ignore = true)
    @Mapping(target = "listePrix", ignore = true)
    CommandeClient toEntity(CommandeClientRequest dto);
    
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "facture", ignore = true)
    @Mapping(target = "entrepot", ignore = true)
    @Mapping(target = "taxes", ignore = true)
    @Mapping(target = "listePrix", ignore = true)
    void updateFromDto(CommandeClientRequest dto, @MappingTarget CommandeClient entity);
}