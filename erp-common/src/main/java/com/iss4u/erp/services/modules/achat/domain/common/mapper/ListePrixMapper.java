package com.iss4u.erp.services.modules.achat.domain.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.ListePrixRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.response.ListePrixResponse;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;

@Mapper(componentModel = "spring")
public interface ListePrixMapper {
    
    @Mapping(target = "prixArticles", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "groupeClients", ignore = true)
    ListePrixResponse toResponse(ListePrix entity);

    @Mapping(target = "prixArticles", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "groupeClients", ignore = true)
    ListePrix toEntity(ListePrixRequest dto);

    @Mapping(target = "prixArticles", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "groupeClients", ignore = true)
    void updateFromDto(ListePrixRequest dto, @MappingTarget ListePrix entity);
}