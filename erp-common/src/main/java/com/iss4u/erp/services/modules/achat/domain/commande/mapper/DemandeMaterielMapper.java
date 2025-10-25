package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.request.DemandeMaterielRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.response.DemandeMaterielResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface DemandeMaterielMapper {
    @Mapping(target = "dateTransaction", expression = "java(parseDate(dto.getDateTransaction()))")
    DemandeMateriel toEntity(DemandeMaterielRequest dto);

    @Mapping(target = "dateTransaction", expression = "java(formatDate(entity.getDateTransaction()))")
    DemandeMaterielResponse toResponse(DemandeMateriel entity);

    @Mapping(target = "dateTransaction", expression = "java(parseDate(dto.getDateTransaction()))")
    void updateFromDto(DemandeMaterielRequest dto, @MappingTarget DemandeMateriel entity);

    default LocalDate parseDate(String dateStr) {
        if (dateStr == null) return null;
        return LocalDate.parse(dateStr);
    }

    default String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.toString();
    }
}