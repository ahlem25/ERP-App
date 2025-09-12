package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.request.LivreInventaireRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.response.LivreInventaireResponse;
import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;

@Mapper(componentModel = "spring")
public interface LivreInventaireMapper {
    LivreInventaireResponse toResponse(LivreInventaire entity);
    LivreInventaire toEntity(LivreInventaireRequest dto);
    void updateFromDto(LivreInventaireRequest dto, @MappingTarget LivreInventaire entity);
}