package com.iss4u.erp.services.modules.core.domain.dto.autorisation.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorisationResponse {
    private Long id;
    private String typeAutorisation;
    private String valeur;
    private Boolean appliquerTousDocuments;


}