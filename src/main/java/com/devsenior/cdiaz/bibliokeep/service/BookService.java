package com.devsenior.cdiaz.bibliokeep.service;

import com.devsenior.cdiaz.bibliokeep.model.dto.BookRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO bookRequestDTO);

    BookResponseDTO getBookById(Long id);

    List<BookResponseDTO> getAllBooksByUser();

    BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO);

    void deleteBook(Long id);
}
