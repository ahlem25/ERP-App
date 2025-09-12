package com.iss4u.erp.services.modules.achat.domain.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.common.dto.regletarification.request.RegleTarificationRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.regletarification.response.RegleTarificationResponse;
import com.iss4u.erp.services.modules.achat.domain.common.models.RegleTarification;

@Mapper(componentModel = "spring")
public interface RegleTarificationMapper {
    RegleTarificationResponse toResponse(RegleTarification entity);

    RegleTarification toEntity(RegleTarificationRequest dto);

    void updateFromDto(RegleTarificationRequest dto, @MappingTarget RegleTarification entity);
}