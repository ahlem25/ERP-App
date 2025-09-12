package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.entrepot.request.EntrepotRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.entrepot.response.EntrepotResponse;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Mapper(componentModel = "spring")
public interface EntrepotMapper {
    EntrepotResponse toResponse(Entrepot entity);
    Entrepot toEntity(EntrepotRequest dto);
    void updateFromDto(EntrepotRequest dto, @MappingTarget Entrepot entity);
}