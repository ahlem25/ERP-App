package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.PrixArticleService;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.request.PrixArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.response.PrixArticleResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prix-articles")
public class PrixArticleController {

    private final PrixArticleService service;

    @GetMapping
    public List<PrixArticleResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<PrixArticleResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public PrixArticleResponse create(@RequestBody PrixArticleRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public PrixArticleResponse update(
        @PathVariable Long id,
        @RequestBody PrixArticleRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}