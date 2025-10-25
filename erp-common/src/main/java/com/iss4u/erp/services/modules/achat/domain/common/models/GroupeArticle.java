package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private boolean estGroupe;
    @ManyToOne
    @JoinColumn(name = "groupe_parent_id")
    @JsonBackReference(value = "groupe-article-parent")
    private GroupeArticle groupeParent;

    @OneToMany(mappedBy = "groupeArticle")
    @JsonManagedReference(value = "groupe-article-articles")
    private List<Article> articles;
}

