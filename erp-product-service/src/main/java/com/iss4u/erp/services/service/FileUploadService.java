package com.iss4u.erp.services.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadFile(MultipartFile file);
    Resource getFile(String filename);
    boolean deleteFile(String filePath);
    String getServiceName();
}
