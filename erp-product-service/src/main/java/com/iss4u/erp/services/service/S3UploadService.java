package com.iss4u.erp.services.service;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService implements FileUploadService {

    private final S3Template s3Template;

    @Value("${file.upload.s3.bucket:erp-app-uploads}")
    private String bucketName;

    @Value("${file.upload.s3.region:eu-west-3}")
    private String region;

    @Value("${file.upload.s3.endpoint:https://erp-app-uploads.s3.eu-west-3.amazonaws.com}")
    private String endpoint;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // Générer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Uploader vers S3
            String s3Url = s3Template.upload(bucketName, uniqueFilename, file.getInputStream(), 
                PutObjectRequest.builder()
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build());

            log.info("Fichier uploadé vers S3: {}", s3Url);
            return s3Url;

        } catch (IOException e) {
            log.error("Erreur lors de l'upload S3 du fichier: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'upload du fichier vers S3", e);
        }
    }

    @Override
    public Resource getFile(String filename) {
        try {
            // Récupérer le fichier depuis S3
            Resource resource = s3Template.download(bucketName, filename);
            
            if (!resource.exists()) {
                log.warn("Fichier non trouvé dans S3: {}/{}", bucketName, filename);
                throw new RuntimeException("Fichier non trouvé: " + filename);
            }
            
            log.info("Fichier récupéré depuis S3: {}/{}", bucketName, filename);
            return resource;
            
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du fichier S3: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération du fichier depuis S3", e);
        }
    }

    @Override
    public String getServiceName() {
        return "S3UploadService";
    }
}
