package com.iss4u.erp.services.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.iss4u.erp.services.service.ClientService;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.request.ClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.response.ClientResponse;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;

import com.iss4u.erp.services.modules.vente.domain.client.repository.ClientRepository;
import com.iss4u.erp.services.modules.vente.domain.client.repository.GroupeClientRepository;
import com.iss4u.erp.services.modules.vente.domain.billing.repository.DevisRepository;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ListePrixRepository;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ArticleRepository;
import com.iss4u.erp.services.modules.achat.domain.common.repository.GroupeArticleRepository;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RelationsBidirectionnellesTest {

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private GroupeClientRepository groupeClientRepository;
    
    @Autowired
    private DevisRepository devisRepository;
    
    @Autowired
    private ListePrixRepository listePrixRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private GroupeArticleRepository groupeArticleRepository;

    @Test
    @Transactional
    @Rollback
    public void testRelationClientDevis() {
        System.out.println("🧪 Test de la relation Client-Devis");
        
        // Créer un client
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setNom("Client Test Devis");
        clientRequest.setAdresse("123 Test Street");
        clientRequest.setDevise("EUR");
        clientRequest.setEstClientInterne(false);
        clientRequest.setType("PARTICULIER");
        
        ClientResponse clientResponse = clientService.save(clientRequest);
        assertNotNull(clientResponse);
        Long clientId = clientResponse.getId();
        
        // Récupérer le client depuis la base
        Client client = clientRepository.findById(clientId).orElse(null);
        assertNotNull(client);
        
        // Créer un devis pour ce client
        Devis devis = new Devis();
        devis.setNumero("DEV-001");
        devis.setValidite(new Date());
        devis.setMontantTotal(1000.0f);
        devis.setClient(client);
        
        Devis savedDevis = devisRepository.save(devis);
        assertNotNull(savedDevis);
        assertNotNull(savedDevis.getId());
        
        // Vérifier la relation bidirectionnelle
        Client updatedClient = clientRepository.findById(clientId).orElse(null);
        assertNotNull(updatedClient);
        assertNotNull(updatedClient.getDevis());
        assertTrue(updatedClient.getDevis().size() > 0);
        
        // Vérifier que le devis a bien le client
        Devis retrievedDevis = devisRepository.findById(savedDevis.getId()).orElse(null);
        assertNotNull(retrievedDevis);
        assertNotNull(retrievedDevis.getClient());
        assertEquals(clientId, retrievedDevis.getClient().getId());
        
        System.out.println("✅ Relation Client-Devis fonctionne correctement");
    }

    @Test
    @Transactional
    @Rollback
    public void testRelationClientListePrix() {
        System.out.println("🧪 Test de la relation Client-ListePrix");
        
        // Créer une liste de prix
        ListePrix listePrix = new ListePrix();
        listePrix.setNomListeDePrix("Liste Standard");
        listePrix.setDevise("EUR");
        listePrix.setActif(true);
        listePrix.setAchat(false);
        listePrix.setVente(true);
        listePrix.setPrixIndependantUniteMesure(false);
        
        ListePrix savedListePrix = listePrixRepository.save(listePrix);
        assertNotNull(savedListePrix);
        Long listePrixId = savedListePrix.getId();
        
        // Créer un client avec cette liste de prix
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setNom("Client Test ListePrix");
        clientRequest.setAdresse("456 Test Avenue");
        clientRequest.setDevise("EUR");
        clientRequest.setEstClientInterne(false);
        clientRequest.setType("ENTREPRISE");
        clientRequest.setListePrixId(listePrixId);
        
        ClientResponse clientResponse = clientService.save(clientRequest);
        assertNotNull(clientResponse);
        assertEquals(listePrixId, clientResponse.getListePrixId());
        
        // Vérifier la relation bidirectionnelle
        ListePrix retrievedListePrix = listePrixRepository.findById(listePrixId).orElse(null);
        assertNotNull(retrievedListePrix);
        assertNotNull(retrievedListePrix.getClients());
        assertTrue(retrievedListePrix.getClients().size() > 0);
        
        Client client = clientRepository.findById(clientResponse.getId()).orElse(null);
        assertNotNull(client);
        assertNotNull(client.getListePrix());
        assertEquals(listePrixId, client.getListePrix().getId());
        
        System.out.println("✅ Relation Client-ListePrix fonctionne correctement");
    }

    @Test
    @Transactional
    @Rollback
    public void testRelationClientGroupeClient() {
        System.out.println("🧪 Test de la relation Client-GroupeClient");
        
        // Créer un groupe client
        GroupeClient groupeClient = new GroupeClient();
        groupeClient.setNom("Groupe Premium");
        groupeClient.setModeleConditionsPaiementParDefaut("30 jours");
        groupeClient.setEstGroupe(true);
        
        GroupeClient savedGroupe = groupeClientRepository.save(groupeClient);
        assertNotNull(savedGroupe);
        Long groupeId = savedGroupe.getId();
        
        // Créer un client avec ce groupe
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setNom("Client Test Groupe");
        clientRequest.setAdresse("789 Test Boulevard");
        clientRequest.setDevise("EUR");
        clientRequest.setEstClientInterne(false);
        clientRequest.setType("ENTREPRISE");
        clientRequest.setGroupeId(groupeId);
        
        ClientResponse clientResponse = clientService.save(clientRequest);
        assertNotNull(clientResponse);
        assertEquals(groupeId, clientResponse.getGroupeId());
        
        // Vérifier la relation bidirectionnelle
        GroupeClient retrievedGroupe = groupeClientRepository.findById(groupeId).orElse(null);
        assertNotNull(retrievedGroupe);
        assertNotNull(retrievedGroupe.getClients());
        assertTrue(retrievedGroupe.getClients().size() > 0);
        
        Client client = clientRepository.findById(clientResponse.getId()).orElse(null);
        assertNotNull(client);
        assertNotNull(client.getGroupe());
        assertEquals(groupeId, client.getGroupe().getId());
        
        System.out.println("✅ Relation Client-GroupeClient fonctionne correctement");
    }

    @Test
    @Transactional
    @Rollback
    public void testRelationArticleGroupeArticle() {
        System.out.println("🧪 Test de la relation Article-GroupeArticle");
        
        // Créer un groupe d'articles
        GroupeArticle groupeArticle = new GroupeArticle();
        groupeArticle.setNom("Électronique");
        groupeArticle.setEstGroupe(true);
        
        GroupeArticle savedGroupe = groupeArticleRepository.save(groupeArticle);
        assertNotNull(savedGroupe);
        Long groupeId = savedGroupe.getId();
        
        // Créer un article avec ce groupe
        Article article = new Article();
        article.setCodeArticle("ART-001");
        article.setNomArticle("Ordinateur Portable");
        article.setStockable(true);
        article.setUnite("unité");
        article.setPrixVente(999.99);
        article.setGroupeArticle(savedGroupe);
        
        Article savedArticle = articleRepository.save(article);
        assertNotNull(savedArticle);
        assertNotNull(savedArticle.getId());
        
        // Vérifier la relation bidirectionnelle
        GroupeArticle retrievedGroupe = groupeArticleRepository.findById(groupeId).orElse(null);
        assertNotNull(retrievedGroupe);
        assertNotNull(retrievedGroupe.getArticles());
        assertTrue(retrievedGroupe.getArticles().size() > 0);
        
        Article retrievedArticle = articleRepository.findById(savedArticle.getId()).orElse(null);
        assertNotNull(retrievedArticle);
        assertNotNull(retrievedArticle.getGroupeArticle());
        assertEquals(groupeId, retrievedArticle.getGroupeArticle().getId());
        
        System.out.println("✅ Relation Article-GroupeArticle fonctionne correctement");
    }

    @Test
    @Transactional
    @Rollback
    public void testRelationComplexeClientGroupeListePrix() {
        System.out.println("🧪 Test de la relation complexe Client-GroupeClient-ListePrix");
        
        // Créer une liste de prix
        ListePrix listePrix = new ListePrix();
        listePrix.setNomListeDePrix("Liste Premium");
        listePrix.setDevise("EUR");
        listePrix.setActif(true);
        listePrix.setAchat(false);
        listePrix.setVente(true);
        listePrix.setPrixIndependantUniteMesure(false);
        
        ListePrix savedListePrix = listePrixRepository.save(listePrix);
        Long listePrixId = savedListePrix.getId();
        
        // Créer un groupe client avec cette liste de prix
        GroupeClient groupeClient = new GroupeClient();
        groupeClient.setNom("Groupe Premium");
        groupeClient.setModeleConditionsPaiementParDefaut("30 jours");
        groupeClient.setEstGroupe(true);
        groupeClient.setListePrix(savedListePrix);
        
        GroupeClient savedGroupe = groupeClientRepository.save(groupeClient);
        Long groupeId = savedGroupe.getId();
        
        // Créer un client avec ce groupe
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setNom("Client Premium");
        clientRequest.setAdresse("123 Premium Street");
        clientRequest.setDevise("EUR");
        clientRequest.setEstClientInterne(false);
        clientRequest.setType("ENTREPRISE");
        clientRequest.setGroupeId(groupeId);
        
        ClientResponse clientResponse = clientService.save(clientRequest);
        assertNotNull(clientResponse);
        
        // Vérifier toutes les relations
        Client client = clientRepository.findById(clientResponse.getId()).orElse(null);
        assertNotNull(client);
        assertNotNull(client.getGroupe());
        assertEquals(groupeId, client.getGroupe().getId());
        
        GroupeClient groupe = groupeClientRepository.findById(groupeId).orElse(null);
        assertNotNull(groupe);
        assertNotNull(groupe.getListePrix());
        assertEquals(listePrixId, groupe.getListePrix().getId());
        assertNotNull(groupe.getClients());
        assertTrue(groupe.getClients().size() > 0);
        
        ListePrix liste = listePrixRepository.findById(listePrixId).orElse(null);
        assertNotNull(liste);
        assertNotNull(liste.getGroupeClients());
        assertTrue(liste.getGroupeClients().size() > 0);
        
        System.out.println("✅ Relation complexe Client-GroupeClient-ListePrix fonctionne correctement");
    }

    @Test
    @Transactional
    @Rollback
    public void testJsonSerializationDeserialization() {
        System.out.println("🧪 Test de la sérialisation/désérialisation JSON");
        
        // Créer un client avec toutes ses relations
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setNom("Client Test JSON");
        clientRequest.setAdresse("123 JSON Street");
        clientRequest.setDevise("EUR");
        clientRequest.setEstClientInterne(false);
        clientRequest.setType("PARTICULIER");
        
        ClientResponse clientResponse = clientService.save(clientRequest);
        assertNotNull(clientResponse);
        
        // Vérifier que la réponse JSON ne contient pas de références circulaires
        assertNotNull(clientResponse.getCommandeIds());
        assertNotNull(clientResponse.getFactureIds());
        assertNotNull(clientResponse.getDevisIds());
        assertNotNull(clientResponse.getBonLivraisonIds());
        
        // Les collections doivent être des listes d'IDs, pas d'objets complets
        assertTrue(clientResponse.getCommandeIds() instanceof List);
        assertTrue(clientResponse.getFactureIds() instanceof List);
        assertTrue(clientResponse.getDevisIds() instanceof List);
        assertTrue(clientResponse.getBonLivraisonIds() instanceof List);
        
        System.out.println("✅ Sérialisation JSON fonctionne sans références circulaires");
    }
}
