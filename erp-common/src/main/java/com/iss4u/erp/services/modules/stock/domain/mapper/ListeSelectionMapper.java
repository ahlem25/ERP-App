package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.listeselection.request.ListeSelectionRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.listeselection.response.ListeSelectionResponse;
import com.iss4u.erp.services.modules.stock.domain.models.ListeSelection;

@Mapper(componentModel = "spring")
public interface ListeSelectionMapper {
    ListeSelectionResponse toResponse(ListeSelection entity);
    ListeSelection toEntity(ListeSelectionRequest dto);
    void updateFromDto(ListeSelectionRequest dto, @MappingTarget ListeSelection entity);
}