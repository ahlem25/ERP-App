package com.iss4u.erp.services.modules.core.domain.dto.commentaire.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireRequest {
    private String contenu;
    private Date dateCreation;


}