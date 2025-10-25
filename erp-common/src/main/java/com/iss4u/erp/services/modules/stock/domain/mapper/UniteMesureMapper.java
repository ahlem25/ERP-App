package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.request.UniteMesureRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.response.UniteMesureResponse;
import com.iss4u.erp.services.modules.stock.domain.models.UniteMesure;

@Mapper(componentModel = "spring")
public interface UniteMesureMapper {
    UniteMesureResponse toResponse(UniteMesure entity);
    UniteMesure toEntity(UniteMesureRequest dto);
    void updateFromDto(UniteMesureRequest dto, @MappingTarget UniteMesure entity);
}