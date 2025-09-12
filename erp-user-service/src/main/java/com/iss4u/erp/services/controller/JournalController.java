package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.JournalService;
import com.iss4u.erp.services.modules.core.domain.dto.journal.request.JournalRequest;
import com.iss4u.erp.services.modules.core.domain.dto.journal.response.JournalResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/journals")
public class JournalController {

    private final JournalService service;

    @GetMapping
    public List<JournalResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<JournalResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public JournalResponse create(@RequestBody JournalRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public JournalResponse update(
        @PathVariable Long id,
        @RequestBody JournalRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}