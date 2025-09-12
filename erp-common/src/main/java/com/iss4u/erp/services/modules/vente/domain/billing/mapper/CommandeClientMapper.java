package com.iss4u.erp.services.modules.vente.domain.billing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.request.CommandeClientRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.response.CommandeClientResponse;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;

@Mapper(componentModel = "spring")
public interface CommandeClientMapper {
    CommandeClientResponse toResponse(CommandeClient entity);
    CommandeClient toEntity(CommandeClientRequest dto);
    void updateFromDto(CommandeClientRequest dto, @MappingTarget CommandeClient entity);
}