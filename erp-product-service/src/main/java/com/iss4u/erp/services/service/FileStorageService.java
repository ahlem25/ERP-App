package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path uploadDir;

    public FileStorageService() throws IOException {
        this.uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDir);
    }

    public String store(MultipartFile file) throws IOException {
        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename());
        String ext = "";
        int dot = original.lastIndexOf('.')
                ;
        if (dot != -1) {
            ext = original.substring(dot);
        }
        String filename = "article_" + UUID.randomUUID() + ext;
        Path target = this.uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    public Path load(String filename) {
        return this.uploadDir.resolve(filename).normalize();
    }
}


