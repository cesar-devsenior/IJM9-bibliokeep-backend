package com.devsenior.cdiaz.bibliokeep.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import com.devsenior.cdiaz.bibliokeep.service.FileService;

class FileServiceImplTest {

    @TempDir
    private Path tempDir;

    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileServiceImpl(tempDir.toString());
    }

    @Test
    void testSaveFileReturnsResponse() {
        var file = new MockMultipartFile(
                "file",
                "file-test.jpg",
                "image/jpeg",
                "content".getBytes());

        var response = fileService.save(file);

        assertNotNull(response);
        assertNotNull(response.fileName());
        assertTrue(response.fileName().endsWith(".webp"));
        assertTrue(tempDir.resolve(response.fileName()).toFile().exists());

    }

    @Test
    void testEmptyFileThrowException() {
        var emptyFile = new MockMultipartFile("file", new byte[0]);

        assertThrows(RuntimeException.class, () -> {
            fileService.save(emptyFile);
        });
    }
}
