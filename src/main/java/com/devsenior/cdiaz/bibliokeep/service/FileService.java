package com.devsenior.cdiaz.bibliokeep.service;

import org.springframework.web.multipart.MultipartFile;

import com.devsenior.cdiaz.bibliokeep.model.dto.UploadResponseDTO;

public interface FileService {
    
    UploadResponseDTO save(MultipartFile file);
}
