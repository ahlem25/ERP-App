package com.iss4u.erp.services.modules.core.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomModule;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference(value = "role-modules")
    private Role role;
}
