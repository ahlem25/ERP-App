package com.iss4u.erp.services.modules.vente.domain.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.request.ClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.response.ClientResponse;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    
    @Mapping(target = "groupeId", source = "groupe.id")
    @Mapping(target = "groupeNom", source = "groupe.nom")
    @Mapping(target = "commandeIds", source = "commandes", qualifiedByName = "mapCommandeIds")
    @Mapping(target = "devisIds", source = "devis", qualifiedByName = "mapDevisIds")
    ClientResponse toResponse(Client entity);
    
    @Mapping(target = "groupe", ignore = true)
    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "devis", ignore = true)
    Client toEntity(ClientRequest dto);
    
    @Mapping(target = "groupe", ignore = true)
    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "devis", ignore = true)
    void updateFromDto(ClientRequest dto, @MappingTarget Client entity);
    
    @org.mapstruct.Named("mapCommandeIds")
    default java.util.List<Long> mapCommandeIds(java.util.List<com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient> commandes) {
        if (commandes == null) return null;
        return commandes.stream().map(com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapDevisIds")
    default java.util.List<Long> mapDevisIds(java.util.List<com.iss4u.erp.services.modules.vente.domain.billing.models.Devis> devis) {
        if (devis == null) return null;
        return devis.stream().map(com.iss4u.erp.services.modules.vente.domain.billing.models.Devis::getId).collect(java.util.stream.Collectors.toList());
    }
}