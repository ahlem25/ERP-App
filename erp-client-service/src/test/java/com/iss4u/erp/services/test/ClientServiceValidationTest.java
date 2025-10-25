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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClientServiceValidationTest {

    @Autowired
    private ClientService clientService;

    @Test
    @Transactional
    @Rollback
    public void testClientServiceNotNull() {
        // Vérifier que le service est bien injecté
        assertNotNull(clientService, "ClientService ne devrait pas être null");
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateClientWithNullValues() {
        // Test avec des valeurs null pour vérifier la robustesse
        ClientRequest request = new ClientRequest();
        // Laisser tous les champs null
        
        try {
            ClientResponse response = clientService.save(request);
            // Si ça passe, vérifier que les valeurs par défaut sont correctes
            assertNotNull(response);
            assertNotNull(response.getId());
        } catch (Exception e) {
            // Si une exception est levée, vérifier qu'elle est appropriée
            assertTrue(e.getMessage().contains("validation") || 
                      e.getMessage().contains("required") ||
                      e.getMessage().contains("constraint"));
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateNonExistentClient() {
        // Test de mise à jour d'un client qui n'existe pas
        ClientRequest request = new ClientRequest();
        request.setNom("Non Existent Client");
        
        try {
            clientService.update(99999L, request);
            fail("Devrait lever une exception pour un client inexistant");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Client not found"));
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testFindNonExistentClient() {
        // Test de recherche d'un client qui n'existe pas
        var result = clientService.findById(99999L);
        assertFalse(result.isPresent());
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteNonExistentClient() {
        // Test de suppression d'un client qui n'existe pas
        try {
            clientService.delete(99999L);
            // Si ça ne lève pas d'exception, c'est OK (Spring Data JPA ne lève pas d'exception)
        } catch (Exception e) {
            // Si une exception est levée, vérifier qu'elle est appropriée
            assertTrue(e.getMessage().contains("not found") || 
                      e.getMessage().contains("does not exist"));
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testClientWithSpecialCharacters() {
        // Test avec des caractères spéciaux
        ClientRequest request = new ClientRequest();
        request.setNom("Client avec caractères spéciaux: éàçù€$");
        request.setAdresse("123 Rue de l'Église, Paris 75001");
        request.setDevise("EUR");
        request.setEstClientInterne(false);
        request.setType("PARTICULIER");
        
        ClientResponse response = clientService.save(request);
        
        assertNotNull(response);
        assertEquals("Client avec caractères spéciaux: éàçù€$", response.getNom());
        assertEquals("123 Rue de l'Église, Paris 75001", response.getAdresse());
    }

    @Test
    @Transactional
    @Rollback
    public void testClientWithLongValues() {
        // Test avec des valeurs très longues
        ClientRequest request = new ClientRequest();
        request.setNom("A".repeat(1000)); // Nom très long
        request.setAdresse("B".repeat(2000)); // Adresse très longue
        request.setDevise("EUR");
        request.setEstClientInterne(false);
        request.setType("PARTICULIER");
        
        try {
            ClientResponse response = clientService.save(request);
            assertNotNull(response);
        } catch (Exception e) {
            // Si une exception est levée à cause de la longueur, c'est normal
            assertTrue(e.getMessage().contains("length") || 
                      e.getMessage().contains("size") ||
                      e.getMessage().contains("constraint"));
        }
    }
}
