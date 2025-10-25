package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
public class UploadController {
    private final FileStorageService storage;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) throws IOException {
        String filename = storage.store(file);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> get(@PathVariable String filename) throws MalformedURLException {
        Path path = storage.load(filename);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}


