package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.request.BonLivraisonRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.response.BonLivraisonResponse;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;

@Mapper(componentModel = "spring")
public interface BonLivraisonMapper {
    BonLivraisonResponse toResponse(BonLivraison entity);
    BonLivraison toEntity(BonLivraisonRequest dto);
    void updateFromDto(BonLivraisonRequest dto, @MappingTarget BonLivraison entity);
}