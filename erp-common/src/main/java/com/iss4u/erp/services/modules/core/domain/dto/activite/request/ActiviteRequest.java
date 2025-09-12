package com.iss4u.erp.services.modules.core.domain.dto.activite.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiviteRequest {
    private String description;
    private Date date;


}