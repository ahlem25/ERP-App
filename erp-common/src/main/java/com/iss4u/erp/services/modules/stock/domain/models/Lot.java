package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Article article;
    private Date dateProduction;
    private Date dateExpiration;
    private Double garantie;
}
