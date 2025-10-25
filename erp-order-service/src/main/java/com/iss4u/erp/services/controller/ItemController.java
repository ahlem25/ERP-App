package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ItemService;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.item.request.ItemRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.item.response.ItemResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService service;

    @GetMapping
    public List<ItemResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ItemResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ItemResponse create(@RequestBody ItemRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ItemResponse update(
        @PathVariable Long id,
        @RequestBody ItemRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}