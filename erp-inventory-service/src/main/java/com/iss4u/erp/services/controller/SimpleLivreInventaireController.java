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
public class SimpleLivreInventaireController {

    private final LivreInventaireService service;

    @GetMapping("/api/v1/livre-inventaires")
    public List<LivreInventaireResponse> getAllLivres() {
        return service.findAll();
    }

    @GetMapping("/api/v1/livre-inventaires/{id}")
    public Optional<LivreInventaireResponse> getLivreById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/api/v1/livre-inventaires")
    public LivreInventaireResponse createLivre(@RequestBody LivreInventaireRequest request) {
        return service.save(request);
    }

    @PutMapping("/api/v1/livre-inventaires/{id}")
    public LivreInventaireResponse updateLivre(
        @PathVariable Long id,
        @RequestBody LivreInventaireRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/api/v1/livre-inventaires/{id}")
    public void deleteLivre(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }

    @GetMapping("/api/v1/ecriture-stocks")
    public String getEcritureStocks() {
        return "EcritureStocks endpoint is working!";
    }
}
