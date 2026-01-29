package com.devsenior.cdiaz.bibliokeep.service;

import com.devsenior.cdiaz.bibliokeep.model.dto.BookRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO bookRequestDTO, UUID userId);

    BookResponseDTO getBookById(Long id, UUID userId);

    List<BookResponseDTO> getAllBooksByUser(UUID userId);

    BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO, UUID userId);

    void deleteBook(Long id, UUID userId);
}
