package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniteMesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Boolean estNombreEntier;
    
    @OneToMany(mappedBy = "unitesMesure")
    @JsonManagedReference(value = "unite-mesure-ecriture-stocks")
    private List<EcritureStock> ecritureStocks;
}
