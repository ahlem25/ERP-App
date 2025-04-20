package com.iss4u.erp.services.modules.core.domain.dto.journal.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalRequest {
    private String typeJournal;
    private Date dateActivite;


}