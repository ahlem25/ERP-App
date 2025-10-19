package com.iss4u.erp.services.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class LocalUploadService implements FileUploadService {

    @Value("${file.upload.local.directory:uploads}")
    private String uploadDirectory;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // Créer le répertoire s'il n'existe pas
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Générer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Sauvegarder le fichier
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Fichier uploadé localement: {}", filePath.toString());
            return filePath.toString();

        } catch (IOException e) {
            log.error("Erreur lors de l'upload local du fichier: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'upload du fichier", e);
        }
    }

    @Override
    public Resource getFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDirectory).resolve(filename);
            
            if (!Files.exists(filePath)) {
                log.warn("Fichier non trouvé: {}", filePath);
                throw new RuntimeException("Fichier non trouvé: " + filename);
            }
            
            Resource resource = new FileSystemResource(filePath);
            if (!resource.exists()) {
                log.warn("Ressource non accessible: {}", filePath);
                throw new RuntimeException("Fichier non accessible: " + filename);
            }
            
            log.info("Fichier récupéré localement: {}", filePath);
            return resource;
            
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du fichier local: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération du fichier", e);
        }
    }

    @Override
    public String getServiceName() {
        return "LocalUploadService";
    }
}
