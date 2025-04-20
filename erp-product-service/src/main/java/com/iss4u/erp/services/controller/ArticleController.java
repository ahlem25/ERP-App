package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ArticleService;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.request.ArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.response.ArticleResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService service;

    @GetMapping
    public List<ArticleResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ArticleResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ArticleResponse create(@RequestBody ArticleRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ArticleResponse update(
        @PathVariable Long id,
        @RequestBody ArticleRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}