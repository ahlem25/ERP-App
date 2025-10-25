package com.iss4u.erp.services.modules.core.domain.dto.activite.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiviteResponse {
    private Long id;
    private String description;
    private Date date;


}