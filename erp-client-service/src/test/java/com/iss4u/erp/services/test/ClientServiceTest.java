package com.iss4u.erp.services.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.iss4u.erp.services.service.ClientService;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.request.ClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.response.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Test
    @Transactional
    @Rollback
    public void testCreateClient() {
        // Test de création d'un client
        ClientRequest request = new ClientRequest();
        request.setNom("Test Client");
        request.setAdresse("123 Test Street");
        request.setDevise("EUR");
        request.setEstClientInterne(false);
        request.setType("PARTICULIER");
        
        ClientResponse response = clientService.save(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Test Client", response.getNom());
        assertEquals("123 Test Street", response.getAdresse());
        assertEquals("EUR", response.getDevise());
        assertFalse(response.getEstClientInterne());
        assertEquals("PARTICULIER", response.getType());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAllClients() {
        // Test de récupération de tous les clients
        List<ClientResponse> clients = clientService.findAll();
        
        assertNotNull(clients);
        // Le test peut passer même si la liste est vide
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateClient() {
        // Créer un client d'abord
        ClientRequest createRequest = new ClientRequest();
        createRequest.setNom("Original Name");
        createRequest.setAdresse("Original Address");
        createRequest.setDevise("EUR");
        createRequest.setEstClientInterne(false);
        createRequest.setType("PARTICULIER");
        
        ClientResponse createdClient = clientService.save(createRequest);
        Long clientId = createdClient.getId();
        
        // Mettre à jour le client
        ClientRequest updateRequest = new ClientRequest();
        updateRequest.setNom("Updated Name");
        updateRequest.setAdresse("Updated Address");
        updateRequest.setDevise("USD");
        updateRequest.setEstClientInterne(true);
        updateRequest.setType("ENTREPRISE");
        
        ClientResponse updatedClient = clientService.update(clientId, updateRequest);
        
        assertNotNull(updatedClient);
        assertEquals(clientId, updatedClient.getId());
        assertEquals("Updated Name", updatedClient.getNom());
        assertEquals("Updated Address", updatedClient.getAdresse());
        assertEquals("USD", updatedClient.getDevise());
        assertTrue(updatedClient.getEstClientInterne());
        assertEquals("ENTREPRISE", updatedClient.getType());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindClientById() {
        // Créer un client d'abord
        ClientRequest request = new ClientRequest();
        request.setNom("Test Client for Find");
        request.setAdresse("Test Address");
        request.setDevise("EUR");
        request.setEstClientInterne(false);
        request.setType("PARTICULIER");
        
        ClientResponse createdClient = clientService.save(request);
        Long clientId = createdClient.getId();
        
        // Rechercher le client par ID
        Optional<ClientResponse> foundClient = clientService.findById(clientId);
        
        assertTrue(foundClient.isPresent());
        assertEquals(clientId, foundClient.get().getId());
        assertEquals("Test Client for Find", foundClient.get().getNom());
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteClient() {
        // Créer un client d'abord
        ClientRequest request = new ClientRequest();
        request.setNom("Client to Delete");
        request.setAdresse("Delete Address");
        request.setDevise("EUR");
        request.setEstClientInterne(false);
        request.setType("PARTICULIER");
        
        ClientResponse createdClient = clientService.save(request);
        Long clientId = createdClient.getId();
        
        // Supprimer le client
        clientService.delete(clientId);
        
        // Vérifier que le client n'existe plus
        Optional<ClientResponse> deletedClient = clientService.findById(clientId);
        assertFalse(deletedClient.isPresent());
    }
}
