package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devsenior.cdiaz.bibliokeep.model.dto.UploadResponseDTO;
import com.devsenior.cdiaz.bibliokeep.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    private final Path root;

    @Value("${app.upload.path-pattern}")
    private String patternPath;

    public FileServiceImpl(@Value("${app.upload.directory}") String uploadDir) {
        root = Paths.get(uploadDir);
    }

    @Override
    public UploadResponseDTO save(MultipartFile file) {

        try {
            // Verificar si la carpeta no existe, crearla
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            // Verificar si el archivo no tiene contenido
            if (file.isEmpty()) {
                throw new RuntimeException("El archivo está vacio");
            }

            var fileName = String.format("%s.webp", UUID.randomUUID().toString());
            Files.copy(file.getInputStream(), root.resolve(fileName));

            var url = String.format("%s/%s", patternPath, fileName) ;
            
            return new UploadResponseDTO(fileName, url, file.getSize());
        } catch (IOException ex) {
            throw new RuntimeException("Error fatal al guardar un archivo en disco");
        }
    }

}
