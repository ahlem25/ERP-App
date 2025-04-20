package com.iss4u.erp.services.modules.vente.domain.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.request.GroupeClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.response.GroupeClientResponse;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;

@Mapper(componentModel = "spring")
public interface GroupeClientMapper {
    GroupeClientResponse toResponse(GroupeClient entity);
    GroupeClient toEntity(GroupeClientRequest dto);
    void updateFromDto(GroupeClientRequest dto, @MappingTarget GroupeClient entity);
}