package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ArticleRepository;
import com.iss4u.erp.services.modules.achat.domain.common.mapper.ArticleMapper;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.request.ArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.response.ArticleResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository repository;
    private final ArticleMapper mapper;
    private final LocalUploadService localUploadService;
    private final S3UploadService s3UploadService;
    
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public List<ArticleResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ArticleResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ArticleResponse save(ArticleRequest request) {
        Article entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ArticleResponse update(Long id, ArticleRequest request) {
        Article existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id " + id));
        
        // Sauvegarder l'ancien chemin d'image avant la mise à jour
        String oldImagePath = existingEntity.getImage();
        String newImagePath = request.getImage();
        
        // Mettre à jour l'entité
        mapper.updateFromDto(request, existingEntity);
        
        // Vérifier si l'image a changé
        if (oldImagePath != null && !oldImagePath.isEmpty() 
                && newImagePath != null && !newImagePath.isEmpty() 
                && !oldImagePath.equals(newImagePath)) {
            // Supprimer l'ancienne image
            deleteArticleImage(oldImagePath);
        }
        
        Article savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        // Récupérer l'article avant de le supprimer pour nettoyer son image
        Optional<Article> articleOptional = repository.findById(id);
        
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            
            // Supprimer l'image si elle existe
            if (article.getImage() != null && !article.getImage().isEmpty()) {
                deleteArticleImage(article.getImage());
            }
        }
        
        // Supprimer l'article
        repository.deleteById(id);
    }
    
    /**
     * Supprime l'image de l'article
     */
    private void deleteArticleImage(String imagePath) {
        try {
            log.info("Attempting to delete article image: {}", imagePath);
            
            // Extraire le fileName du chemin complet
            String fileName = extractFileNameFromPath(imagePath);
            log.info("Extracted fileName from article image: {}", fileName);
            
            // Sélectionner le service selon le profil
            FileUploadService uploadService = selectUploadService();
            
            // Supprimer le fichier
            boolean deleted = uploadService.deleteFile(fileName);
            
            if (deleted) {
                log.info("Article image deleted successfully: {}", fileName);
            } else {
                log.warn("Failed to delete article image: {}", fileName);
            }
            
        } catch (Exception e) {
            log.error("Failed to delete article image: {}", imagePath, e);
            // Ne pas lancer d'exception pour ne pas bloquer la suppression de l'article
        }
    }
    
    /**
     * Sélectionne le service d'upload selon le profil
     */
    private FileUploadService selectUploadService() {
        if ("default".equals(activeProfile)) {
            log.info("Sélection du service d'upload local pour le profil default");
            return localUploadService;
        } else {
            log.info("Sélection du service d'upload S3 pour le profil: {}", activeProfile);
            return s3UploadService;
        }
    }
    
    /**
     * Extrait le nom du fichier d'un chemin complet (URL S3 ou chemin local)
     */
    private String extractFileNameFromPath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return filePath;
        }
        
        // Extraire le nom du fichier (dernier élément après le dernier /)
        String[] parts = filePath.replace("\\", "/").split("/");
        return parts[parts.length - 1];
    }
}