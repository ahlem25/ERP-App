package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.marque.request.MarqueRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.marque.response.MarqueResponse;
import com.iss4u.erp.services.modules.stock.domain.models.Marque;

@Mapper(componentModel = "spring")
public interface MarqueMapper {
    MarqueResponse toResponse(Marque entity);
    Marque toEntity(MarqueRequest dto);
    void updateFromDto(MarqueRequest dto, @MappingTarget Marque entity);
}