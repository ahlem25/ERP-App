package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.GroupeArticleService;
import com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.request.GroupeArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.response.GroupeArticleResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groupes-article")
public class GroupeArticleController {

    private final GroupeArticleService service;
    @GetMapping
    public List<GroupeArticleResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<GroupeArticleResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public GroupeArticleResponse create(@RequestBody GroupeArticleRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public GroupeArticleResponse update(
        @PathVariable Long id,
        @RequestBody GroupeArticleRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}