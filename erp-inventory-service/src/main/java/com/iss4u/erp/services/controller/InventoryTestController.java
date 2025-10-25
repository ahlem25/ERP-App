package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.LivreInventaireService;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.request.LivreInventaireRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.response.LivreInventaireResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InventoryTestController {

    private final LivreInventaireService service;

    @GetMapping("/hello")
    public String hello() {
        return "Hello! Inventory service is working!";
    }

    @GetMapping("/livres")
    public List<LivreInventaireResponse> getAllLivres() {
        return service.findAll();
    }

    @GetMapping("/livres/{id}")
    public Optional<LivreInventaireResponse> getLivreById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/livres")
    public LivreInventaireResponse createLivre(@RequestBody LivreInventaireRequest request) {
        return service.save(request);
    }

    @PutMapping("/livres/{id}")
    public LivreInventaireResponse updateLivre(
        @PathVariable Long id,
        @RequestBody LivreInventaireRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/livres/{id}")
    public void deleteLivre(@PathVariable Long id) {
        service.delete(id);
    }
}
