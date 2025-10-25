package com.iss4u.erp.services.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.iss4u.erp.services.service.FactureAchatService;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.FactureAchatRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.ItemRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.response.FactureAchatResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.FactureAchatRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class FactureAchatUpdateTest {

    @Autowired
    private FactureAchatService factureAchatService;
    
    @Autowired
    private FactureAchatRepository factureAchatRepository;

    @Test
    @Transactional
    @Rollback
    public void testUpdateFactureAchatWithModification() {
        System.out.println("🧪 Test de modification FactureAchat - Scénario problématique");
        
        // Étape 1: Créer une facture d'achat initiale
        FactureAchatRequest createRequest = new FactureAchatRequest();
        createRequest.setSerie("FA-TEST-001");
        createRequest.setDate(LocalDate.now());
        createRequest.setHeurePublication(LocalTime.now());
        createRequest.setModifierDateHeurePublication(false);
        createRequest.setDateEcheance(LocalDate.now().plusDays(30));
        createRequest.setEstPaye(false);
        createRequest.setEstRetour(false);
        createRequest.setAppliquerRetenueImpot(false);
        createRequest.setNumeroFactureFournisseur("FOURN-TEST-001");
        createRequest.setDateFactureFournisseur(LocalDate.now());
        createRequest.setCentreDeCouts("CENTRE-TEST");
        createRequest.setProjet("PROJET-TEST");
        createRequest.setDevise("EUR");
        
        // Articles initiaux
        List<ItemRequest> initialArticles = new ArrayList<>();
        
        ItemRequest item1 = new ItemRequest();
        item1.setQuantite(10.0);
        item1.setPrixUnitaire(25.50);
        item1.setCommentaire("Article initial 1");
        initialArticles.add(item1);
        
        ItemRequest item2 = new ItemRequest();
        item2.setQuantite(5.0);
        item2.setPrixUnitaire(15.75);
        item2.setCommentaire("Article initial 2");
        initialArticles.add(item2);
        
        createRequest.setArticlesFactures(initialArticles);
        
        // Sauvegarder la facture initiale
        FactureAchatResponse createdResponse = factureAchatService.save(createRequest);
        Long factureId = createdResponse.getId();
        
        assertNotNull(factureId);
        assertEquals("FA-TEST-001", createdResponse.getSerie());
        System.out.println("✅ FactureAchat créée avec ID: " + factureId);
        
        // Étape 2: Première modification - changer les articles
        FactureAchatRequest updateRequest1 = new FactureAchatRequest();
        updateRequest1.setSerie("FA-TEST-001-UPDATED");
        updateRequest1.setDate(LocalDate.now());
        updateRequest1.setHeurePublication(LocalTime.now());
        updateRequest1.setModifierDateHeurePublication(true);
        updateRequest1.setDateEcheance(LocalDate.now().plusDays(45));
        updateRequest1.setEstPaye(false);
        updateRequest1.setEstRetour(false);
        updateRequest1.setAppliquerRetenueImpot(true);
        updateRequest1.setNumeroFactureFournisseur("FOURN-TEST-001-UPDATED");
        updateRequest1.setDateFactureFournisseur(LocalDate.now());
        updateRequest1.setCentreDeCouts("CENTRE-TEST-UPDATED");
        updateRequest1.setProjet("PROJET-TEST-UPDATED");
        updateRequest1.setDevise("USD");
        
        // Nouveaux articles pour la première modification
        List<ItemRequest> updatedArticles1 = new ArrayList<>();
        
        ItemRequest updatedItem1 = new ItemRequest();
        updatedItem1.setQuantite(20.0);
        updatedItem1.setPrixUnitaire(30.0);
        updatedItem1.setCommentaire("Article modifié 1");
        updatedArticles1.add(updatedItem1);
        
        ItemRequest updatedItem2 = new ItemRequest();
        updatedItem2.setQuantite(15.0);
        updatedItem2.setPrixUnitaire(40.0);
        updatedItem2.setCommentaire("Article modifié 2");
        updatedArticles1.add(updatedItem2);
        
        ItemRequest updatedItem3 = new ItemRequest();
        updatedItem3.setQuantite(8.0);
        updatedItem3.setPrixUnitaire(12.5);
        updatedItem3.setCommentaire("Article modifié 3");
        updatedArticles1.add(updatedItem3);
        
        updateRequest1.setArticlesFactures(updatedArticles1);
        
        // Première mise à jour
        FactureAchatResponse updatedResponse1 = factureAchatService.update(factureId, updateRequest1);
        
        assertNotNull(updatedResponse1);
        assertEquals(factureId, updatedResponse1.getId());
        assertEquals("FA-TEST-001-UPDATED", updatedResponse1.getSerie());
        assertEquals("USD", updatedResponse1.getDevise());
        assertTrue(updatedResponse1.isAppliquerRetenueImpot());
        System.out.println("✅ Première modification réussie");
        
        // Étape 3: Deuxième modification - réduire les articles
        FactureAchatRequest updateRequest2 = new FactureAchatRequest();
        updateRequest2.setSerie("FA-TEST-001-FINAL");
        updateRequest2.setDate(LocalDate.now());
        updateRequest2.setHeurePublication(LocalTime.now());
        updateRequest2.setModifierDateHeurePublication(true);
        updateRequest2.setDateEcheance(LocalDate.now().plusDays(60));
        updateRequest2.setEstPaye(false);
        updateRequest2.setEstRetour(false);
        updateRequest2.setAppliquerRetenueImpot(false);
        updateRequest2.setNumeroFactureFournisseur("FOURN-TEST-001-FINAL");
        updateRequest2.setDateFactureFournisseur(LocalDate.now());
        updateRequest2.setCentreDeCouts("CENTRE-TEST-FINAL");
        updateRequest2.setProjet("PROJET-TEST-FINAL");
        updateRequest2.setDevise("EUR");
        
        // Moins d'articles pour la deuxième modification
        List<ItemRequest> updatedArticles2 = new ArrayList<>();
        
        ItemRequest finalItem1 = new ItemRequest();
        finalItem1.setQuantite(25.0);
        finalItem1.setPrixUnitaire(35.0);
        finalItem1.setCommentaire("Article final 1");
        updatedArticles2.add(finalItem1);
        
        updateRequest2.setArticlesFactures(updatedArticles2);
        
        // Deuxième mise à jour
        FactureAchatResponse updatedResponse2 = factureAchatService.update(factureId, updateRequest2);
        
        assertNotNull(updatedResponse2);
        assertEquals(factureId, updatedResponse2.getId());
        assertEquals("FA-TEST-001-FINAL", updatedResponse2.getSerie());
        assertEquals("EUR", updatedResponse2.getDevise());
        assertFalse(updatedResponse2.isAppliquerRetenueImpot());
        System.out.println("✅ Deuxième modification réussie");
        
        // Étape 4: Troisième modification - vider les articles
        FactureAchatRequest updateRequest3 = new FactureAchatRequest();
        updateRequest3.setSerie("FA-TEST-001-EMPTY");
        updateRequest3.setDate(LocalDate.now());
        updateRequest3.setHeurePublication(LocalTime.now());
        updateRequest3.setModifierDateHeurePublication(false);
        updateRequest3.setDateEcheance(LocalDate.now().plusDays(30));
        updateRequest3.setEstPaye(false);
        updateRequest3.setEstRetour(false);
        updateRequest3.setAppliquerRetenueImpot(false);
        updateRequest3.setNumeroFactureFournisseur("FOURN-TEST-001-EMPTY");
        updateRequest3.setDateFactureFournisseur(LocalDate.now());
        updateRequest3.setCentreDeCouts("CENTRE-TEST-EMPTY");
        updateRequest3.setProjet("PROJET-TEST-EMPTY");
        updateRequest3.setDevise("EUR");
        
        // Aucun article
        updateRequest3.setArticlesFactures(new ArrayList<>());
        
        // Troisième mise à jour
        FactureAchatResponse updatedResponse3 = factureAchatService.update(factureId, updateRequest3);
        
        assertNotNull(updatedResponse3);
        assertEquals(factureId, updatedResponse3.getId());
        assertEquals("FA-TEST-001-EMPTY", updatedResponse3.getSerie());
        System.out.println("✅ Troisième modification (liste vide) réussie");
        
        // Étape 5: Quatrième modification - ajouter des articles à nouveau
        FactureAchatRequest updateRequest4 = new FactureAchatRequest();
        updateRequest4.setSerie("FA-TEST-001-RESTORED");
        updateRequest4.setDate(LocalDate.now());
        updateRequest4.setHeurePublication(LocalTime.now());
        updateRequest4.setModifierDateHeurePublication(true);
        updateRequest4.setDateEcheance(LocalDate.now().plusDays(90));
        updateRequest4.setEstPaye(false);
        updateRequest4.setEstRetour(false);
        updateRequest4.setAppliquerRetenueImpot(true);
        updateRequest4.setNumeroFactureFournisseur("FOURN-TEST-001-RESTORED");
        updateRequest4.setDateFactureFournisseur(LocalDate.now());
        updateRequest4.setCentreDeCouts("CENTRE-TEST-RESTORED");
        updateRequest4.setProjet("PROJET-TEST-RESTORED");
        updateRequest4.setDevise("USD");
        
        // Restaurer des articles
        List<ItemRequest> restoredArticles = new ArrayList<>();
        
        ItemRequest restoredItem1 = new ItemRequest();
        restoredItem1.setQuantite(100.0);
        restoredItem1.setPrixUnitaire(50.0);
        restoredItem1.setCommentaire("Article restauré 1");
        restoredArticles.add(restoredItem1);
        
        ItemRequest restoredItem2 = new ItemRequest();
        restoredItem2.setQuantite(200.0);
        restoredItem2.setPrixUnitaire(75.0);
        restoredItem2.setCommentaire("Article restauré 2");
        restoredArticles.add(restoredItem2);
        
        updateRequest4.setArticlesFactures(restoredArticles);
        
        // Quatrième mise à jour
        FactureAchatResponse updatedResponse4 = factureAchatService.update(factureId, updateRequest4);
        
        assertNotNull(updatedResponse4);
        assertEquals(factureId, updatedResponse4.getId());
        assertEquals("FA-TEST-001-RESTORED", updatedResponse4.getSerie());
        assertEquals("USD", updatedResponse4.getDevise());
        assertTrue(updatedResponse4.isAppliquerRetenueImpot());
        System.out.println("✅ Quatrième modification (restauration) réussie");
        
        System.out.println("🎉 Tous les tests de modification ont réussi !");
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateFactureAchatNullArticles() {
        System.out.println("🧪 Test de modification FactureAchat avec articles null");
        
        // Créer une facture avec des articles
        FactureAchatRequest createRequest = new FactureAchatRequest();
        createRequest.setSerie("FA-NULL-TEST");
        createRequest.setDate(LocalDate.now());
        createRequest.setDevise("EUR");
        
        List<ItemRequest> articles = new ArrayList<>();
        ItemRequest item = new ItemRequest();
        item.setQuantite(1.0);
        item.setPrixUnitaire(10.0);
        item.setCommentaire("Test");
        articles.add(item);
        createRequest.setArticlesFactures(articles);
        
        FactureAchatResponse createdResponse = factureAchatService.save(createRequest);
        Long factureId = createdResponse.getId();
        
        // Modifier avec articles null
        FactureAchatRequest updateRequest = new FactureAchatRequest();
        updateRequest.setSerie("FA-NULL-TEST-UPDATED");
        updateRequest.setDate(LocalDate.now());
        updateRequest.setDevise("USD");
        updateRequest.setArticlesFactures(null); // Articles null
        
        FactureAchatResponse updatedResponse = factureAchatService.update(factureId, updateRequest);
        
        assertNotNull(updatedResponse);
        assertEquals(factureId, updatedResponse.getId());
        assertEquals("FA-NULL-TEST-UPDATED", updatedResponse.getSerie());
        assertEquals("USD", updatedResponse.getDevise());
        
        System.out.println("✅ Modification avec articles null réussie");
    }

    @Test
    @Transactional
    @Rollback
    public void testMultipleRapidUpdates() {
        System.out.println("🧪 Test de modifications rapides multiples");
        
        // Créer une facture
        FactureAchatRequest createRequest = new FactureAchatRequest();
        createRequest.setSerie("FA-RAPID-TEST");
        createRequest.setDate(LocalDate.now());
        createRequest.setDevise("EUR");
        
        List<ItemRequest> articles = new ArrayList<>();
        ItemRequest item = new ItemRequest();
        item.setQuantite(1.0);
        item.setPrixUnitaire(10.0);
        item.setCommentaire("Initial");
        articles.add(item);
        createRequest.setArticlesFactures(articles);
        
        FactureAchatResponse createdResponse = factureAchatService.save(createRequest);
        Long factureId = createdResponse.getId();
        
        // Effectuer plusieurs modifications rapides
        for (int i = 1; i <= 5; i++) {
            FactureAchatRequest updateRequest = new FactureAchatRequest();
            updateRequest.setSerie("FA-RAPID-TEST-" + i);
            updateRequest.setDate(LocalDate.now());
            updateRequest.setDevise(i % 2 == 0 ? "USD" : "EUR");
            
            List<ItemRequest> updateArticles = new ArrayList<>();
            ItemRequest updateItem = new ItemRequest();
            updateItem.setQuantite(i * 10.0);
            updateItem.setPrixUnitaire(i * 5.0);
            updateItem.setCommentaire("Update " + i);
            updateArticles.add(updateItem);
            updateRequest.setArticlesFactures(updateArticles);
            
            FactureAchatResponse updatedResponse = factureAchatService.update(factureId, updateRequest);
            
            assertNotNull(updatedResponse);
            assertEquals(factureId, updatedResponse.getId());
            assertEquals("FA-RAPID-TEST-" + i, updatedResponse.getSerie());
            
            System.out.println("✅ Modification rapide " + i + " réussie");
        }
        
        System.out.println("🎉 Toutes les modifications rapides ont réussi !");
    }
}
