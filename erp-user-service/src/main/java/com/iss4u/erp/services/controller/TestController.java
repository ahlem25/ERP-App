package com.iss4u.erp.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/utilisateurs")
    public ResponseEntity<List<Map<String, Object>>> getUtilisateursFromMySQL() {
        List<Map<String, Object>> utilisateurs = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, email, nom_utilisateur, prenom, nom_famille, actif, derniere_maj FROM utilisateurs";
            
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                
                while (resultSet.next()) {
                    Map<String, Object> utilisateur = new HashMap<>();
                    utilisateur.put("id", resultSet.getLong("id"));
                    utilisateur.put("email", resultSet.getString("email"));
                    utilisateur.put("nomUtilisateur", resultSet.getString("nom_utilisateur"));
                    utilisateur.put("prenom", resultSet.getString("prenom"));
                    utilisateur.put("nomFamille", resultSet.getString("nom_famille"));
                    utilisateur.put("actif", resultSet.getBoolean("actif"));
                    utilisateur.put("derniereMaj", resultSet.getTimestamp("derniere_maj"));
                    
                    // Ajouter des rôles par défaut
                    List<Map<String, Object>> roles = new ArrayList<>();
                    Map<String, Object> role = new HashMap<>();
                    role.put("id", 1);
                    role.put("nom", "UTILISATEUR");
                    roles.add(role);
                    utilisateur.put("roles", roles);
                    
                    utilisateurs.add(utilisateur);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
        
        return ResponseEntity.ok(utilisateurs);
    }
}
