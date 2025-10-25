package com.iss4u.erp.services.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.iss4u.erp.services.service.ListePrixService;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.ListePrixRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.PrixArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.response.ListePrixResponse;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ListePrixRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ListePrixOrphanRemovalTest {

    @Autowired
    private ListePrixService listePrixService;
    
    @Autowired
    private ListePrixRepository listePrixRepository;

    @Test
    @Transactional
    @Rollback
    public void testCreateListePrixWithPrixArticles() {
        System.out.println("🧪 Test de création ListePrix avec prix articles");
        
        // Créer une liste de prix avec des prix articles
        ListePrixRequest request = new ListePrixRequest();
        request.setNomListeDePrix("Liste Test");
        request.setDevise("EUR");
        request.setActif(true);
        request.setAchat(false);
        request.setVente(true);
        request.setPrixIndependantUniteMesure(false);
        
        // Créer des prix articles
        List<PrixArticleRequest> prixArticles = new ArrayList<>();
        
        PrixArticleRequest prix1 = new PrixArticleRequest();
        prix1.setCodeArticle("ART-001");
        prix1.setUniteDeMesure("unité");
        prix1.setUniteEmballage("carton");
        prix1.setQuantiteParUOM(10);
        prix1.setNumeroLot("LOT-001");
        prix1.setAchat(false);
        prix1.setVente(true);
        prix1.setDevise("EUR");
        prix1.setTaux(new BigDecimal("25.50"));
        prix1.setValableAPartirDu(LocalDate.now());
        prix1.setValableJusquau(LocalDate.now().plusDays(30));
        prix1.setDelaiLivraisonJours(5);
        prix1.setNote("Prix de test");
        prixArticles.add(prix1);
        
        PrixArticleRequest prix2 = new PrixArticleRequest();
        prix2.setCodeArticle("ART-002");
        prix2.setUniteDeMesure("kg");
        prix2.setUniteEmballage("sac");
        prix2.setQuantiteParUOM(1);
        prix2.setNumeroLot("LOT-002");
        prix2.setAchat(false);
        prix2.setVente(true);
        prix2.setDevise("EUR");
        prix2.setTaux(new BigDecimal("15.75"));
        prix2.setValableAPartirDu(LocalDate.now());
        prix2.setValableJusquau(LocalDate.now().plusDays(30));
        prix2.setDelaiLivraisonJours(3);
        prix2.setNote("Prix de test 2");
        prixArticles.add(prix2);
        
        request.setPrixArticles(prixArticles);
        
        // Sauvegarder la liste de prix
        ListePrixResponse response = listePrixService.save(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Liste Test", response.getNomListeDePrix());
        assertEquals("EUR", response.getDevise());
        assertTrue(response.isActif());
        assertTrue(response.isVente());
        assertFalse(response.isAchat());
        
        System.out.println("✅ ListePrix créée avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateListePrixWithPrixArticles() {
        System.out.println("🧪 Test de mise à jour ListePrix avec prix articles");
        
        // Créer une liste de prix d'abord
        ListePrixRequest createRequest = new ListePrixRequest();
        createRequest.setNomListeDePrix("Liste Initiale");
        createRequest.setDevise("EUR");
        createRequest.setActif(true);
        createRequest.setAchat(false);
        createRequest.setVente(true);
        createRequest.setPrixIndependantUniteMesure(false);
        
        List<PrixArticleRequest> initialPrix = new ArrayList<>();
        PrixArticleRequest initialPrixArticle = new PrixArticleRequest();
        initialPrixArticle.setCodeArticle("ART-INITIAL");
        initialPrixArticle.setUniteDeMesure("unité");
        initialPrixArticle.setTaux(new BigDecimal("10.0"));
        initialPrixArticle.setVente(true);
        initialPrix.add(initialPrixArticle);
        createRequest.setPrixArticles(initialPrix);
        
        ListePrixResponse createdResponse = listePrixService.save(createRequest);
        Long listePrixId = createdResponse.getId();
        
        // Mettre à jour la liste de prix avec de nouveaux prix articles
        ListePrixRequest updateRequest = new ListePrixRequest();
        updateRequest.setNomListeDePrix("Liste Mise à Jour");
        updateRequest.setDevise("USD");
        updateRequest.setActif(true);
        updateRequest.setAchat(false);
        updateRequest.setVente(true);
        updateRequest.setPrixIndependantUniteMesure(true);
        
        List<PrixArticleRequest> updatedPrix = new ArrayList<>();
        
        PrixArticleRequest updatedPrix1 = new PrixArticleRequest();
        updatedPrix1.setCodeArticle("ART-UPDATED-1");
        updatedPrix1.setUniteDeMesure("unité");
        updatedPrix1.setTaux(new BigDecimal("30.0"));
        updatedPrix1.setVente(true);
        updatedPrix.add(updatedPrix1);
        
        PrixArticleRequest updatedPrix2 = new PrixArticleRequest();
        updatedPrix2.setCodeArticle("ART-UPDATED-2");
        updatedPrix2.setUniteDeMesure("kg");
        updatedPrix2.setTaux(new BigDecimal("40.0"));
        updatedPrix2.setVente(true);
        updatedPrix.add(updatedPrix2);
        
        updateRequest.setPrixArticles(updatedPrix);
        
        // Mettre à jour la liste de prix
        ListePrixResponse updatedResponse = listePrixService.update(listePrixId, updateRequest);
        
        assertNotNull(updatedResponse);
        assertEquals(listePrixId, updatedResponse.getId());
        assertEquals("Liste Mise à Jour", updatedResponse.getNomListeDePrix());
        assertEquals("USD", updatedResponse.getDevise());
        assertTrue(updatedResponse.isPrixIndependantUniteMesure());
        
        System.out.println("✅ ListePrix mise à jour avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testListePrixWithoutPrixArticles() {
        System.out.println("🧪 Test de ListePrix sans prix articles");
        
        // Créer une liste de prix sans prix articles
        ListePrixRequest request = new ListePrixRequest();
        request.setNomListeDePrix("Liste Vide");
        request.setDevise("EUR");
        request.setActif(true);
        request.setAchat(false);
        request.setVente(true);
        request.setPrixIndependantUniteMesure(false);
        request.setPrixArticles(null); // Pas de prix articles
        
        // Sauvegarder la liste de prix
        ListePrixResponse response = listePrixService.save(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Liste Vide", response.getNomListeDePrix());
        
        System.out.println("✅ ListePrix sans prix articles créée avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testListePrixEmptyPrixArticles() {
        System.out.println("🧪 Test de ListePrix avec liste de prix articles vide");
        
        // Créer une liste de prix avec une liste de prix articles vide
        ListePrixRequest request = new ListePrixRequest();
        request.setNomListeDePrix("Liste Vide Prix");
        request.setDevise("EUR");
        request.setActif(true);
        request.setAchat(false);
        request.setVente(true);
        request.setPrixIndependantUniteMesure(false);
        request.setPrixArticles(new ArrayList<>()); // Liste vide
        
        // Sauvegarder la liste de prix
        ListePrixResponse response = listePrixService.save(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Liste Vide Prix", response.getNomListeDePrix());
        
        System.out.println("✅ ListePrix avec liste vide créée avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testOrphanRemovalFix() {
        System.out.println("🧪 Test de la correction orphanRemoval pour ListePrix");
        
        // Ce test vérifie que l'erreur orphanRemoval ne se produit plus
        // en créant et mettant à jour plusieurs fois une liste de prix
        
        ListePrixRequest request = new ListePrixRequest();
        request.setNomListeDePrix("Liste Orphan Test");
        request.setDevise("EUR");
        request.setActif(true);
        request.setAchat(false);
        request.setVente(true);
        request.setPrixIndependantUniteMesure(false);
        
        // Premier ensemble de prix articles
        List<PrixArticleRequest> prix1 = new ArrayList<>();
        PrixArticleRequest prixArticle1 = new PrixArticleRequest();
        prixArticle1.setCodeArticle("TEST-1");
        prixArticle1.setUniteDeMesure("unité");
        prixArticle1.setTaux(new BigDecimal("10.0"));
        prixArticle1.setVente(true);
        prix1.add(prixArticle1);
        request.setPrixArticles(prix1);
        
        ListePrixResponse response1 = listePrixService.save(request);
        Long listePrixId = response1.getId();
        
        // Deuxième ensemble de prix articles
        List<PrixArticleRequest> prix2 = new ArrayList<>();
        PrixArticleRequest prixArticle2 = new PrixArticleRequest();
        prixArticle2.setCodeArticle("TEST-2");
        prixArticle2.setUniteDeMesure("kg");
        prixArticle2.setTaux(new BigDecimal("20.0"));
        prixArticle2.setVente(true);
        prix2.add(prixArticle2);
        
        PrixArticleRequest prixArticle3 = new PrixArticleRequest();
        prixArticle3.setCodeArticle("TEST-3");
        prixArticle3.setUniteDeMesure("m");
        prixArticle3.setTaux(new BigDecimal("30.0"));
        prixArticle3.setVente(true);
        prix2.add(prixArticle3);
        
        request.setPrixArticles(prix2);
        ListePrixResponse response2 = listePrixService.update(listePrixId, request);
        
        // Troisième ensemble de prix articles (liste vide)
        request.setPrixArticles(new ArrayList<>());
        ListePrixResponse response3 = listePrixService.update(listePrixId, request);
        
        // Quatrième ensemble de prix articles
        List<PrixArticleRequest> prix4 = new ArrayList<>();
        PrixArticleRequest prixArticle4 = new PrixArticleRequest();
        prixArticle4.setCodeArticle("TEST-4");
        prixArticle4.setUniteDeMesure("L");
        prixArticle4.setTaux(new BigDecimal("40.0"));
        prixArticle4.setVente(true);
        prix4.add(prixArticle4);
        
        request.setPrixArticles(prix4);
        ListePrixResponse response4 = listePrixService.update(listePrixId, request);
        
        // Si on arrive ici sans exception, la correction fonctionne
        assertNotNull(response4);
        assertEquals(listePrixId, response4.getId());
        
        System.out.println("✅ Correction orphanRemoval validée pour ListePrix - aucune erreur détectée");
    }
}
