package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.soldestock.request.SoldeStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.soldestock.response.SoldeStockResponse;
import com.iss4u.erp.services.modules.stock.domain.models.SoldeStock;

@Mapper(componentModel = "spring")
public interface SoldeStockMapper {
    SoldeStockResponse toResponse(SoldeStock entity);
    SoldeStock toEntity(SoldeStockRequest dto);
    void updateFromDto(SoldeStockRequest dto, @MappingTarget SoldeStock entity);
}