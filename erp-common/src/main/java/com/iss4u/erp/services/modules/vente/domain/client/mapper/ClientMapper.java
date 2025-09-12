package com.iss4u.erp.services.modules.vente.domain.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.request.ClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.response.ClientResponse;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponse toResponse(Client entity);
    Client toEntity(ClientRequest dto);
    void updateFromDto(ClientRequest dto, @MappingTarget Client entity);
}