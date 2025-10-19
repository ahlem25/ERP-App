package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.service.FileUploadService;
import com.iss4u.erp.services.service.LocalUploadService;
import com.iss4u.erp.services.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileUploadController {

    private final LocalUploadService localUploadService;
    private final S3UploadService s3UploadService;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Tentative d'upload de fichier avec le profil: {}", activeProfile);
            
            // Sélectionner le service selon le profil
            FileUploadService uploadService = selectUploadService();
            
            // Uploader le fichier
            String filePath = uploadService.uploadFile(file);
            
            // Préparer la réponse
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fichier uploadé avec succès");
            response.put("filePath", filePath);
            response.put("service", uploadService.getServiceName());
            response.put("originalFilename", file.getOriginalFilename());
            response.put("size", file.getSize());
            
            log.info("Upload réussi avec le service: {}", uploadService.getServiceName());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erreur lors de l'upload du fichier: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors de l'upload du fichier: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/upload/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            log.info("Tentative de récupération du fichier: {} avec le profil: {}", filename, activeProfile);
            
            // Sélectionner le service selon le profil
            FileUploadService uploadService = selectUploadService();
            
            // Récupérer le fichier
            Resource resource = uploadService.getFile(filename);
            
            // Déterminer le type de contenu
            String contentType = "application/octet-stream";
            if (filename.toLowerCase().endsWith(".pdf")) {
                contentType = "application/pdf";
            } else if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (filename.toLowerCase().endsWith(".png")) {
                contentType = "image/png";
            } else if (filename.toLowerCase().endsWith(".gif")) {
                contentType = "image/gif";
            } else if (filename.toLowerCase().endsWith(".txt")) {
                contentType = "text/plain";
            }
            
            log.info("Fichier récupéré avec succès: {} via le service: {}", filename, uploadService.getServiceName());
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
            
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du fichier {}: {}", filename, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private FileUploadService selectUploadService() {
        if ("dev".equals(activeProfile)) {
            log.info("Sélection du service d'upload local pour le profil dev");
            return localUploadService;
        } else {
            log.info("Sélection du service d'upload S3 pour le profil: {}", activeProfile);
            return s3UploadService;
        }
    }
}
