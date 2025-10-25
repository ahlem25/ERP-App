package com.iss4u.erp.services.modules.vente.domain.payment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.vente.domain.sales.models.ProfilPDV;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodePaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String type;
    
    @ManyToOne
    @JoinColumn(name = "profil_pdv_id")
    @JsonBackReference(value = "profil-pdv-methodes-paiement")
    private ProfilPDV profilPDV;
}
