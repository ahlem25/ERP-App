package com.iss4u.erp.services.service;

import com.iss4u.erp.models.Utilisateur;
import com.iss4u.erp.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        System.out.println("📊 Récupération de tous les utilisateurs depuis la base de données");
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        System.out.println("✅ " + utilisateurs.size() + " utilisateurs récupérés");
        return utilisateurs;
    }

    public Utilisateur getUtilisateurById(Long id) {
        System.out.println("🔍 Recherche de l'utilisateur avec l'ID: " + id);
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            System.out.println("✅ Utilisateur trouvé: " + utilisateur.get().getEmail());
            return utilisateur.get();
        } else {
            System.out.println("❌ Utilisateur non trouvé avec l'ID: " + id);
            return null;
        }
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        System.out.println("➕ Création d'un nouvel utilisateur: " + utilisateur.getEmail());
        utilisateur.setDerniereMaj(LocalDateTime.now());
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        System.out.println("✅ Utilisateur créé avec l'ID: " + savedUtilisateur.getId());
        return savedUtilisateur;
    }

    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        System.out.println("🔄 Mise à jour de l'utilisateur: " + utilisateur.getEmail());
        if (utilisateurRepository.existsById(utilisateur.getId())) {
            utilisateur.setDerniereMaj(LocalDateTime.now());
            Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
            System.out.println("✅ Utilisateur mis à jour avec succès");
            return updatedUtilisateur;
        } else {
            System.out.println("❌ Utilisateur non trouvé pour la mise à jour");
            return null;
        }
    }

    public boolean deleteUtilisateur(Long id) {
        System.out.println("🗑️ Suppression de l'utilisateur avec l'ID: " + id);
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            System.out.println("✅ Utilisateur supprimé avec succès");
            return true;
        } else {
            System.out.println("❌ Utilisateur non trouvé pour la suppression");
            return false;
        }
    }

    public List<Utilisateur> searchUtilisateurs(String query) {
        System.out.println("🔍 Recherche d'utilisateurs avec la requête: " + query);
        List<Utilisateur> utilisateurs = utilisateurRepository.findByEmailContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrNomFamilleContainingIgnoreCase(
            query, query, query);
        System.out.println("✅ " + utilisateurs.size() + " utilisateurs trouvés");
        return utilisateurs;
    }

    public Utilisateur toggleUtilisateurStatus(Long id) {
        System.out.println("🔄 Changement de statut de l'utilisateur: " + id);
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            utilisateur.setActif(!utilisateur.isActif());
            utilisateur.setDerniereMaj(LocalDateTime.now());
            Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
            System.out.println("✅ Statut modifié: " + (updatedUtilisateur.isActif() ? "Actif" : "Inactif"));
            return updatedUtilisateur;
        } else {
            System.out.println("❌ Utilisateur non trouvé pour le changement de statut");
            return null;
        }
    }
}