package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.receptionachat.request.ReceptionAchatRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.receptionachat.response.ReceptionAchatResponse;
import com.iss4u.erp.services.modules.stock.domain.models.ReceptionAchat;

@Mapper(componentModel = "spring")
public interface ReceptionAchatMapper {
    ReceptionAchatResponse toResponse(ReceptionAchat entity);
    ReceptionAchat toEntity(ReceptionAchatRequest dto);
    void updateFromDto(ReceptionAchatRequest dto, @MappingTarget ReceptionAchat entity);
}