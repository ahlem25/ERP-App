package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.request.NumeroSerieRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.response.NumeroSerieResponse;
import com.iss4u.erp.services.modules.stock.domain.models.NumeroSerie;

@Mapper(componentModel = "spring")
public interface NumeroSerieMapper {
    NumeroSerieResponse toResponse(NumeroSerie entity);
    NumeroSerie toEntity(NumeroSerieRequest dto);
    void updateFromDto(NumeroSerieRequest dto, @MappingTarget NumeroSerie entity);
}