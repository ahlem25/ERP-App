package com.iss4u.erp.services.modules.core.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeJournal;
    private Date dateActivite;
    
    @ManyToOne
    @JoinColumn(name = "document_id")
    @JsonBackReference(value = "document-journaux")
    private Document document;
}
