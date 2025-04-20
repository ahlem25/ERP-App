package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.lot.request.LotRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.lot.response.LotResponse;
import com.iss4u.erp.services.modules.stock.domain.models.Lot;

@Mapper(componentModel = "spring")
public interface LotMapper {
    LotResponse toResponse(Lot entity);
    Lot toEntity(LotRequest dto);
    void updateFromDto(LotRequest dto, @MappingTarget Lot entity);
}