package com.iss4u.erp.services.modules.core.domain.dto.journal.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalResponse {
    private Long id;
    private String typeJournal;
    private Date dateActivite;


}