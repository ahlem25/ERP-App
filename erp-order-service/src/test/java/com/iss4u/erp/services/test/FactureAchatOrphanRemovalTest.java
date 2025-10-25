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
public class FactureAchatOrphanRemovalTest {

    @Autowired
    private FactureAchatService factureAchatService;
    
    @Autowired
    private FactureAchatRepository factureAchatRepository;

    @Test
    @Transactional
    @Rollback
    public void testCreateFactureAchatWithItems() {
        System.out.println("🧪 Test de création FactureAchat avec articles");
        
        // Créer une facture d'achat avec des articles
        FactureAchatRequest request = new FactureAchatRequest();
        request.setSerie("FA-001");
        request.setDate(LocalDate.now());
        request.setHeurePublication(LocalTime.now());
        request.setModifierDateHeurePublication(false);
        request.setDateEcheance(LocalDate.now().plusDays(30));
        request.setEstPaye(false);
        request.setEstRetour(false);
        request.setAppliquerRetenueImpot(false);
        request.setNumeroFactureFournisseur("FOURN-001");
        request.setDateFactureFournisseur(LocalDate.now());
        request.setCentreDeCouts("CENTRE-001");
        request.setProjet("PROJET-001");
        request.setDevise("EUR");
        
        // Créer des articles facturés
        List<ItemRequest> articles = new ArrayList<>();
        
        ItemRequest item1 = new ItemRequest();
        item1.setQuantite(10.0);
        item1.setPrixUnitaire(25.50);
        item1.setCommentaire("Article 1");
        articles.add(item1);
        
        ItemRequest item2 = new ItemRequest();
        item2.setQuantite(5.0);
        item2.setPrixUnitaire(15.75);
        item2.setCommentaire("Article 2");
        articles.add(item2);
        
        request.setArticlesFactures(articles);
        
        // Sauvegarder la facture
        FactureAchatResponse response = factureAchatService.save(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("FA-001", response.getSerie());
        assertEquals("EUR", response.getDevise());
        
        System.out.println("✅ FactureAchat créée avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateFactureAchatWithItems() {
        System.out.println("🧪 Test de mise à jour FactureAchat avec articles");
        
        // Créer une facture d'abord
        FactureAchatRequest createRequest = new FactureAchatRequest();
        createRequest.setSerie("FA-002");
        createRequest.setDate(LocalDate.now());
        createRequest.setDevise("EUR");
        
        List<ItemRequest> initialArticles = new ArrayList<>();
        ItemRequest initialItem = new ItemRequest();
        initialItem.setQuantite(1.0);
        initialItem.setPrixUnitaire(10.0);
        initialItem.setCommentaire("Article initial");
        initialArticles.add(initialItem);
        createRequest.setArticlesFactures(initialArticles);
        
        FactureAchatResponse createdResponse = factureAchatService.save(createRequest);
        Long factureId = createdResponse.getId();
        
        // Mettre à jour la facture avec de nouveaux articles
        FactureAchatRequest updateRequest = new FactureAchatRequest();
        updateRequest.setSerie("FA-002-UPDATED");
        updateRequest.setDate(LocalDate.now());
        updateRequest.setDevise("USD");
        
        List<ItemRequest> updatedArticles = new ArrayList<>();
        
        ItemRequest updatedItem1 = new ItemRequest();
        updatedItem1.setQuantite(20.0);
        updatedItem1.setPrixUnitaire(30.0);
        updatedItem1.setCommentaire("Article mis à jour 1");
        updatedArticles.add(updatedItem1);
        
        ItemRequest updatedItem2 = new ItemRequest();
        updatedItem2.setQuantite(15.0);
        updatedItem2.setPrixUnitaire(40.0);
        updatedItem2.setCommentaire("Article mis à jour 2");
        updatedArticles.add(updatedItem2);
        
        updateRequest.setArticlesFactures(updatedArticles);
        
        // Mettre à jour la facture
        FactureAchatResponse updatedResponse = factureAchatService.update(factureId, updateRequest);
        
        assertNotNull(updatedResponse);
        assertEquals(factureId, updatedResponse.getId());
        assertEquals("FA-002-UPDATED", updatedResponse.getSerie());
        assertEquals("USD", updatedResponse.getDevise());
        
        System.out.println("✅ FactureAchat mise à jour avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testFactureAchatWithoutItems() {
        System.out.println("🧪 Test de FactureAchat sans articles");
        
        // Créer une facture sans articles
        FactureAchatRequest request = new FactureAchatRequest();
        request.setSerie("FA-003");
        request.setDate(LocalDate.now());
        request.setDevise("EUR");
        request.setArticlesFactures(null); // Pas d'articles
        
        // Sauvegarder la facture
        FactureAchatResponse response = factureAchatService.save(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("FA-003", response.getSerie());
        
        System.out.println("✅ FactureAchat sans articles créée avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testFactureAchatEmptyItems() {
        System.out.println("🧪 Test de FactureAchat avec liste d'articles vide");
        
        // Créer une facture avec une liste d'articles vide
        FactureAchatRequest request = new FactureAchatRequest();
        request.setSerie("FA-004");
        request.setDate(LocalDate.now());
        request.setDevise("EUR");
        request.setArticlesFactures(new ArrayList<>()); // Liste vide
        
        // Sauvegarder la facture
        FactureAchatResponse response = factureAchatService.save(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("FA-004", response.getSerie());
        
        System.out.println("✅ FactureAchat avec liste vide créée avec succès");
    }

    @Test
    @Transactional
    @Rollback
    public void testOrphanRemovalFix() {
        System.out.println("🧪 Test de la correction orphanRemoval");
        
        // Ce test vérifie que l'erreur orphanRemoval ne se produit plus
        // en créant et mettant à jour plusieurs fois une facture
        
        FactureAchatRequest request = new FactureAchatRequest();
        request.setSerie("FA-ORPHAN-TEST");
        request.setDate(LocalDate.now());
        request.setDevise("EUR");
        
        // Premier ensemble d'articles
        List<ItemRequest> articles1 = new ArrayList<>();
        ItemRequest item1 = new ItemRequest();
        item1.setQuantite(1.0);
        item1.setPrixUnitaire(10.0);
        item1.setCommentaire("Test 1");
        articles1.add(item1);
        request.setArticlesFactures(articles1);
        
        FactureAchatResponse response1 = factureAchatService.save(request);
        Long factureId = response1.getId();
        
        // Deuxième ensemble d'articles
        List<ItemRequest> articles2 = new ArrayList<>();
        ItemRequest item2 = new ItemRequest();
        item2.setQuantite(2.0);
        item2.setPrixUnitaire(20.0);
        item2.setCommentaire("Test 2");
        articles2.add(item2);
        
        ItemRequest item3 = new ItemRequest();
        item3.setQuantite(3.0);
        item3.setPrixUnitaire(30.0);
        item3.setCommentaire("Test 3");
        articles2.add(item3);
        
        request.setArticlesFactures(articles2);
        FactureAchatResponse response2 = factureAchatService.update(factureId, request);
        
        // Troisième ensemble d'articles (liste vide)
        request.setArticlesFactures(new ArrayList<>());
        FactureAchatResponse response3 = factureAchatService.update(factureId, request);
        
        // Quatrième ensemble d'articles
        List<ItemRequest> articles4 = new ArrayList<>();
        ItemRequest item4 = new ItemRequest();
        item4.setQuantite(4.0);
        item4.setPrixUnitaire(40.0);
        item4.setCommentaire("Test 4");
        articles4.add(item4);
        
        request.setArticlesFactures(articles4);
        FactureAchatResponse response4 = factureAchatService.update(factureId, request);
        
        // Si on arrive ici sans exception, la correction fonctionne
        assertNotNull(response4);
        assertEquals(factureId, response4.getId());
        
        System.out.println("✅ Correction orphanRemoval validée - aucune erreur détectée");
    }
}
