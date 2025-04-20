package com.iss4u.erp.services.modules.core.domain.dto.autorisation.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorisationRequest {
    private String typeAutorisation;
    private String valeur;
    private Boolean appliquerTousDocuments;


}