package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.request.VieillissementStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.response.VieillissementStockResponse;
import com.iss4u.erp.services.modules.stock.domain.models.VieillissementStock;

@Mapper(componentModel = "spring")
public interface VieillissementStockMapper {
    VieillissementStockResponse toResponse(VieillissementStock entity);
    VieillissementStock toEntity(VieillissementStockRequest dto);
    void updateFromDto(VieillissementStockRequest dto, @MappingTarget VieillissementStock entity);
}