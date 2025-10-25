package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.request.EcritureStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.response.EcritureStockResponse;
import com.iss4u.erp.services.modules.stock.domain.models.EcritureStock;

@Mapper(componentModel = "spring")
public interface EcritureStockMapper {
    EcritureStockResponse toResponse(EcritureStock entity);
    EcritureStock toEntity(EcritureStockRequest dto);
    void updateFromDto(EcritureStockRequest dto, @MappingTarget EcritureStock entity);
}